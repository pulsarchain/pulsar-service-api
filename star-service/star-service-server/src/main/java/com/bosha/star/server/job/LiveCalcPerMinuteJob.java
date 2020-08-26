package com.bosha.star.server.job;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.stream.Collectors;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.bosha.commons.config.GlobalExecutorService;
import com.bosha.commons.utils.StrUtils;
import com.bosha.contract.api.entity.ContractTransferTask;
import com.bosha.contract.api.service.ContractTransferTaskService;
import com.bosha.finance.api.entity.ContractMiningDetail;
import com.bosha.finance.api.enums.FinanceServiceTypeEnum;
import com.bosha.finance.api.service.ContractMiningDetailService;
import com.bosha.star.api.constants.StarServiceConstants;
import com.bosha.star.api.dto.server.LiveMiningCalcLog;
import com.bosha.star.api.dto.web.ImGroupPushMessage;
import com.bosha.star.api.entity.LiveMining;
import com.bosha.star.api.entity.LiveMiningRecord;
import com.bosha.star.api.entity.StarMember;
import com.bosha.star.api.enums.LiveMiningChatRoomPushEnum;
import com.bosha.star.api.enums.LiveMiningTypeEnum;
import com.bosha.star.api.service.StarService;
import com.bosha.star.server.mapper.LiveMiningMapper;
import com.bosha.star.server.mapper.LiveMiningRecordMapper;
import com.bosha.star.server.mapper.StarMemberMapper;
import com.bosha.star.server.redis.CacheKey;
import com.bosha.star.server.utils.ImUtils;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: LiveCalcPerMinuteJob
 * @Author liqingping
 * @Date 2020-03-29 12:23
 */
@Component
@Slf4j
public class LiveCalcPerMinuteJob {

    private static final BigDecimal MIN_REWARD = BigDecimal.valueOf(0.01);

    private static final String MINING_MSG = "第%s分钟 %s挖出%sPUL";

    private static final List<Integer> OTHER_TYPES = Arrays.asList(LiveMiningTypeEnum.VIEW.getType(), LiveMiningTypeEnum.LIKE.getType(), LiveMiningTypeEnum.GIFT.getType());

    @Autowired
    private RedissonClient redis;
    @Autowired
    private LiveMiningMapper liveMiningMapper;
    @Autowired
    private LiveMiningRecordMapper liveMiningRecordMapper;
    @Autowired
    private StarMemberMapper starMemberMapper;
    @Autowired
    private StarService starService;
    @Autowired
    private ContractTransferTaskService contractTransferTaskService;
    @Autowired
    private ContractMiningDetailService contractMiningDetailService;
    @Autowired
    private LiveCloseJob liveCloseJob;
    @Autowired
    private ImUtils imUtils;

    @XxlJob("LiveCalcPerMinuteJob")//每1min运行一次
    public ReturnT<String> calc(String s) {
        try {
            long timeMillis = System.currentTimeMillis();
            DateTime endTime = DateUtil.parse(DateUtil.format(new Date(), StarServiceConstants.SECOND_FORMAT));
            DateTime startTime = DateUtil.offsetMinute(endTime, -1);
            List<LiveMining> liveMinings = liveMiningMapper.calcPerMinute(endTime);
            if (CollectionUtils.isEmpty(liveMinings))
                return ReturnT.SUCCESS;
            List<Future<String>> futures = new ArrayList<>(liveMinings.size());
            log.info("【直播挖矿结算】准备计算挖矿奖励，startTime={}，endTime={}，size={}，{}",
                    startTime, endTime, liveMinings.size(), liveMinings.stream().map(LiveMining::getTitle).collect(Collectors.toList()));
            for (LiveMining liveMining : liveMinings) {
                if (liveMining.getActualLiveStartTime() == null) {
                    log.info("【直播挖矿结算】实际开播时间为空，未开播过，由LiveCloseJob执行，id={}，title={}", liveMining.getId(), liveMining.getTitle());
                    continue;
                }
                Future<String> future = GlobalExecutorService.executorService.submit(() -> {
                    BigDecimal giveOut = liveMiningRecordMapper.giveOut(liveMining.getId(), null);
                    if (giveOut.compareTo(liveMining.getAmount()) >= 0) {
                        log.info("【直播挖矿结算】币全部发完！id={}，title={}", liveMining.getId(), liveMining.getTitle());
                        outOfMoney(liveMining.getId(), null, true);
                        return "";
                    }
                    DateTime actualStartLiveTime = DateUtil.parse(DateUtil.format(liveMining.getActualLiveStartTime(), StarServiceConstants.SECOND_FORMAT));
                    long totalCalcDiff = DateUtil.between(actualStartLiveTime, liveMining.getLiveEndTime(), DateUnit.MINUTE);
                    long diff = DateUtil.between(actualStartLiveTime, startTime, DateUnit.MINUTE, true) + 1;
                    long diffMinute = DateUtil.between(liveMining.getLiveStartTime(), startTime, DateUnit.MINUTE, true) + 1;

                    List<LiveMiningRecord> liveMiningRecords = liveMiningRecordMapper.calcPerMinute(liveMining.getId(), startTime, endTime);
                    boolean lastTime = endTime.equals(liveMining.getLiveEndTime());
                    if (CollectionUtils.isEmpty(liveMiningRecords)) {
                        if (lastTime) {
                            BigDecimal left = liveMining.getAmount().subtract(giveOut);
                            liveCloseJob.donate(liveMining, left);
                            log.info("【直播挖矿结算】最后一次无人挖矿，id={}，title={}，准备捐赠没有挖完的奖励={}", liveMining.getId(), liveMining.getTitle(), left);
                        }
                        return "id=" + liveMining.getId() + ",title=" + liveMining.getTitle() + "，当前时间 [ " + startTime + " - " + endTime + " ] 无人挖矿";
                    }

                    Set<Long> starIds = liveMiningRecords.stream().map(LiveMiningRecord::getStarId).collect(Collectors.toSet());
                    Map<Long, BigDecimal> starCalcMap = new HashMap<>();
                    for (Long starId : starIds) {
                        int countMember = starMemberMapper.countMember(starId);
                        long count = liveMiningRecords.stream().filter(liveMiningRecord -> liveMiningRecord.getStarId().equals(starId)).count();
                        starCalcMap.put(starId, BigDecimal.valueOf(count).divide(BigDecimal.valueOf(countMember), 8, BigDecimal.ROUND_HALF_DOWN));
                    }

                    BigDecimal shareGiveOut = liveMiningRecordMapper.giveOut(liveMining.getId(), Collections.singletonList(LiveMiningTypeEnum.SHARE.getType()));
                    BigDecimal avgShare = liveMining.getSharePul(); //liveMining.getSharePul().divide(BigDecimal.valueOf(liveMining.getShareNum()), 2, BigDecimal.ROUND_DOWN);
                    if (shareGiveOut.compareTo(liveMining.getSharePul().multiply(BigDecimal.valueOf(liveMining.getShareNum()))) >= 0) {
                        log.warn("【直播挖矿结算】转发类型，已发完所有奖励，avgShare将分配为0，id={}", liveMining.getId());
                        avgShare = BigDecimal.ZERO;
                    }
                    BigDecimal commentGiveOut = liveMiningRecordMapper.giveOut(liveMining.getId(), Collections.singletonList(LiveMiningTypeEnum.COMMENT.getType()));
                    BigDecimal avgComment = liveMining.getCommentPul();  //liveMining.getCommentPul().divide(BigDecimal.valueOf(liveMining.getCommentNum()), 2, BigDecimal.ROUND_DOWN);
                    if (commentGiveOut.compareTo(liveMining.getCommentPul().multiply(BigDecimal.valueOf(liveMining.getCommentNum()))) >= 0) {
                        log.warn("【直播挖矿结算】评论类型，已发完所有奖励，avgComment将分配为0，id={}", liveMining.getId());
                        avgComment = BigDecimal.ZERO;
                    }
                    List<LiveMiningRecord> shareAndCommentList = new ArrayList<>();
                    List<LiveMiningRecord> othertList = new ArrayList<>();
                    List<ContractTransferTask> taskList = new ArrayList<>();
                    List<String> noticeList = new ArrayList<>();
                    liveMiningRecords.forEach(liveMiningRecord -> {
                        if (liveMiningRecord.getType().equals(LiveMiningTypeEnum.SHARE.getType()) || liveMiningRecord.getType().equals(LiveMiningTypeEnum.COMMENT.getType())) {
                            shareAndCommentList.add(liveMiningRecord);
                        } else
                            othertList.add(liveMiningRecord);
                    });
                    Date date = new Date();
                    LiveMiningCalcLog calcLog = new LiveMiningCalcLog();
                    BigDecimal logLeftAmount = BigDecimal.ZERO;
                    Map<String, BigDecimal> addressCalcMap = new HashMap<>();
                    BigDecimal totalCalcPerMin = othertList.stream().map(liveMiningRecord -> {
                        StarMember starMember = starService.getStarMemberFromCache(liveMiningRecord.getAddress());
                        BigDecimal calc = starCalcMap.get(starMember.getStarId()).multiply(starMember.getCurrentHz());
                        addressCalcMap.put(starMember.getAddress(), calc);
                        return calc;
                    }).reduce(BigDecimal.ZERO, BigDecimal::add);
                    for (LiveMiningRecord record : shareAndCommentList) {
                        if (record.getType().equals(LiveMiningTypeEnum.SHARE.getType())) {
                            record.setAmount(avgShare);
                            calcLog.setShare(calcLog.getShare() + 1);
                            calcLog.setShareAmount(calcLog.getShareAmount().add(avgShare));
                        } else {
                            record.setAmount(avgComment);
                            calcLog.setComment(calcLog.getComment() + 1);
                            calcLog.setCommentAmount(calcLog.getCommentAmount().add(avgComment));
                        }
                        record.setStatus(1);
                        record.setUpdateTime(date);
                        liveMiningRecordMapper.updateByPrimaryKeySelective(record);
                        ContractTransferTask build = ContractTransferTask.builder().extraId(record.getId())
                                .contractAddress(liveMining.getContractAddress()).amount(record.getAmount()).createTime(date)
                                .serviceType(convert(LiveMiningTypeEnum.getInstance(record.getType())).getType()).systemAddress(liveMining.getSystemAddress())
                                .status(2).toAddress(record.getAddress())
                                .build();
                        taskList.add(build);
                        noticeList.add(String.format(MINING_MSG, diffMinute, record.getRemark(), record.getAmount().setScale(2, BigDecimal.ROUND_DOWN)));
                    }
                    out:
                    if (CollectionUtils.isNotEmpty(othertList)) {
                        RBucket<Long> bucket = redis.getBucket(CacheKey.Live.LIVE_MINING_VIEW_LIKE_GIFT_CASH_OUT.getKey() + liveMining);
                        if (bucket.isExists()) {
                            liveMiningRecordMapper.giveOutUpdate(liveMining.getId(), OTHER_TYPES);
                            break out;
                        }
                        BigDecimal restGiveOut = liveMiningRecordMapper.giveOut(liveMining.getId(), OTHER_TYPES);
                        BigDecimal currentViewAndLikeAndGiftLeft = liveMining.getView().subtract(restGiveOut);
                        logLeftAmount = restGiveOut;
                        BigDecimal avgPerMinTotal = currentViewAndLikeAndGiftLeft.divide(BigDecimal.valueOf(totalCalcDiff - diff + 1), 8, BigDecimal.ROUND_DOWN);
                        BigDecimal avgPerMinPerCalc = avgPerMinTotal.divide(totalCalcPerMin, 8, BigDecimal.ROUND_DOWN);
                        calcLog.setAvgPerMinTotal(avgPerMinTotal);
                        calcLog.setAvgPerMinPerCalc(avgPerMinPerCalc);

                        long totalNum = currentViewAndLikeAndGiftLeft.divide(MIN_REWARD).longValue();
                        if (totalNum == 1) {
                            LiveMiningRecord record = othertList.get(0);
                            record.setAmount(currentViewAndLikeAndGiftLeft);
                            record.setStatus(1);
                            record.setUpdateTime(date);
                            liveMiningRecordMapper.updateByPrimaryKeySelective(record);
                            ContractTransferTask build = ContractTransferTask.builder().extraId(record.getId())
                                    .contractAddress(liveMining.getContractAddress()).amount(record.getAmount()).createTime(date).extraId(record.getId())
                                    .serviceType(convert(LiveMiningTypeEnum.getInstance(record.getType())).getType()).systemAddress(liveMining.getSystemAddress())
                                    .status(2).toAddress(record.getAddress())
                                    .build();
                            taskList.add(build);
                            noticeList.add(String.format(MINING_MSG, diffMinute, record.getRemark(), record.getAmount().setScale(2, BigDecimal.ROUND_DOWN)));
                            log.info("【直播挖矿结算】id={}，title={}，剩余币={}，只够一个人结算！！", liveMining.getId(), liveMining.getTitle(), currentViewAndLikeAndGiftLeft);
                            outOfMoney(liveMining.getId(), OTHER_TYPES, false);
                            calcLog(record, calcLog);
                        } else {
                            for (LiveMiningRecord record : othertList) {
                                LiveMiningTypeEnum typeEnum = LiveMiningTypeEnum.getInstance(record.getType());
                                BigDecimal amount = addressCalcMap.get(record.getAddress()).multiply(avgPerMinPerCalc).setScale(2, BigDecimal.ROUND_DOWN);
                                if (amount.compareTo(MIN_REWARD) < 0) {
                                    record.setAmount(MIN_REWARD);
                                } else
                                    record.setAmount(amount);
                                currentViewAndLikeAndGiftLeft = currentViewAndLikeAndGiftLeft.subtract(record.getAmount());
                                if (currentViewAndLikeAndGiftLeft.compareTo(MIN_REWARD) < 0) {
                                    record.setAmount(currentViewAndLikeAndGiftLeft.add(record.getAmount()));
                                    outOfMoney(liveMining.getId(), OTHER_TYPES, false);
                                    break;
                                }
                                record.setStatus(1);
                                record.setUpdateTime(date);
                                liveMiningRecordMapper.updateByPrimaryKeySelective(record);
                                ContractTransferTask build = ContractTransferTask.builder().extraId(record.getId())
                                        .contractAddress(liveMining.getContractAddress()).amount(record.getAmount()).createTime(date)
                                        .serviceType(convert(typeEnum).getType()).systemAddress(liveMining.getSystemAddress())
                                        .status(2).toAddress(record.getAddress())
                                        .build();
                                taskList.add(build);
                                noticeList.add(String.format(MINING_MSG, diffMinute, record.getRemark(), record.getAmount().setScale(2, BigDecimal.ROUND_DOWN)));
                                calcLog(record, calcLog);
                            }
                        }
                    }
                    log.info("【直播挖矿计算】id={}，第[ {} ]分钟结算，结算size={}， 耗时：{} ms，总量={}，已发放总量={}，剩余总量={}\n" +
                                    "转发总量=【{} 次 - {} - avg={}，已发放={}，本次发放=[ {} 个 - total={} ]】\n" +
                                    "留言总量=【{} 次 - {} - avg={}，已发放={}，本次发放=[ {} 个 - total={} ]】\n" +
                                    "其他类型=【总量={}，已发放={}，本次发放={}，个数={}】\n" +
                                    "详情：avgPerMinTotal={}，avgPerMinPerCalc={}，观看=【{} - {}】，点赞=【{} - {}】，礼物=【{} - {}】",
                            liveMining.getId(), diffMinute, liveMiningRecords.size(), System.currentTimeMillis() - timeMillis,
                            liveMining.getAmount(), giveOut, liveMining.getAmount().subtract(giveOut)
                            , liveMining.getShareNum(), liveMining.getSharePul(), avgShare, shareGiveOut, calcLog.getShare(), calcLog.getShareAmount()
                            , liveMining.getCommentNum(), liveMining.getCommentPul(), avgComment, commentGiveOut, calcLog.getComment(), calcLog.getCommentAmount()
                            , liveMining.getView(), logLeftAmount, calcLog.getLikeAmount().add(calcLog.getGiftAmount()).add(calcLog.getViewAmount()), calcLog.getLike() + calcLog.getView() + calcLog.getGift()
                            , calcLog.getAvgPerMinTotal(), calcLog.getAvgPerMinPerCalc(),
                            calcLog.getView(), calcLog.getViewAmount(), calcLog.getLike(), calcLog.getLikeAmount(), calcLog.getGift(), calcLog.getGiftAmount());
                    taskList.removeIf(contractTransferTask -> contractTransferTask.getAmount().compareTo(BigDecimal.ZERO) <= 0);
                    GlobalExecutorService.executorService.execute(() -> {
                        Map<String, List<LiveMiningRecord>> map = othertList.stream().collect(Collectors.groupingBy(LiveMiningRecord::getRemark));
                        map.forEach((k, values) -> {
                            BigDecimal reduce = values.stream().map(LiveMiningRecord::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, BigDecimal.ROUND_DOWN);
                            log.debug("【直播挖矿结算】{} 挖出 {}", k, reduce.toPlainString());
                        });
                        if (lastTime) {
                            liveMiningRecordMapper.lastTimeLogInfo(liveMining.getId()).forEach(r -> log.debug("【结算分组total】{} 挖出 {}", r.getRemark(), r.getAmount()));
                        }
                    });
                    if (CollectionUtils.isNotEmpty(taskList)) {
                        List<ContractMiningDetail> contractMiningDetailList = new ArrayList<>();
                        for (ContractTransferTask task : taskList) {
                            contractMiningDetailList.add(
                                    ContractMiningDetail.builder()
                                            .status(0).address(task.getToAddress()).amount(task.getAmount()).contractAddress(task.getContractAddress())
                                            .createTime(task.getCreateTime()).relatedId(task.getExtraId()).serviceType(task.getServiceType())
                                            .remark(StrUtils.cutStr(liveMining.getTitle(), 12))
                                            .build());
                        }
                        Map<String, Long> financeMap = contractMiningDetailService.batchInsertWithType(contractMiningDetailList);
                        taskList.forEach(task -> task.setFinanceId(financeMap.get(task.getToAddress() + task.getServiceType())));
                        contractTransferTaskService.insertBatch(taskList);
                        BigDecimal total = taskList.stream().map(ContractTransferTask::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                        log.info("【直播挖矿结算】添加打币任务，id={}，title={}，时间={}，size={}，totalAmount={}", liveMining.getId(), liveMining.getTitle(), startTime, taskList.size(), total);
                    }
                    if (CollectionUtils.isNotEmpty(noticeList))
                        GlobalExecutorService.executorService.execute(() -> {
                            int size = noticeList.size();
                            int batch = 50;
                            int time = size % batch == 0 ? size / batch : (size / batch + 1);
                            for (int i = 1; i <= time; i++) {
                                List<String> list = noticeList.stream().skip((i - 1) * batch).limit(batch).collect(Collectors.toList());
                                imUtils.pushNotice(liveMining.getId(), ImGroupPushMessage.builder().type(LiveMiningChatRoomPushEnum.LIVE_MINING_INFO).data(list).build());
                            }
                        });
                    return "id=" + liveMining.getId() + ",title=" + liveMining.getTitle() + "，当前时间 [ " + startTime + " - " + endTime + " ] ,size=" + liveMiningRecords.size();
                });
                futures.add(future);
            }
            for (Future<String> future : futures) {
                String result = future.get();
                if (StringUtils.isNotBlank(result))
                    log.info("【直播挖矿结算】耗时：{} ms，{}", System.currentTimeMillis() - timeMillis, result);
            }
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ReturnT.FAIL;
        }
    }


    public void outOfMoney(Long id, List<Integer> types, boolean outOfMoney) {
        if (outOfMoney) {
            liveMiningMapper.updateByPrimaryKeySelective(LiveMining.builder().id(id).leftAmount(BigDecimal.ZERO).updateTime(new Date()).build());
            redis.getBucket(CacheKey.Live.LIVE_MINING.getKey() + id).delete();
        } else {
            RBucket<Long> bucket = redis.getBucket(CacheKey.Live.LIVE_MINING_VIEW_LIKE_GIFT_CASH_OUT.getKey() + id);
            bucket.set(id, CacheKey.Live.LIVE_MINING_VIEW_LIKE_GIFT_CASH_OUT.getExpireTime(), CacheKey.Live.LIVE_MINING_VIEW_LIKE_GIFT_CASH_OUT.getTimeUnit());
        }
        liveMiningRecordMapper.giveOutUpdate(id, types);
        log.info("【直播挖矿计算】id={}，币 {} ", id, outOfMoney ? "全部发放完毕" : "观看、点赞等全部发放完毕");
        if (outOfMoney ||
                (!redis.getAtomicLong(CacheKey.Live.LIVE_MINING_SHARE_NUM.getKey() + id).isExists()
                        && !redis.getAtomicLong(CacheKey.Live.LIVE_MINING_COMMENT_NUM.getKey() + id).isExists()))
            imUtils.pushNotice(id, ImGroupPushMessage.builder().type(LiveMiningChatRoomPushEnum.LIVE_OUT_OF_MONEY).data("").build());
    }

    private static FinanceServiceTypeEnum convert(LiveMiningTypeEnum typeEnum) {
        switch (typeEnum) {
            case VIEW:
                return FinanceServiceTypeEnum.LIVE_MINING_VIEW;
            case SHARE:
                return FinanceServiceTypeEnum.LIVE_MINING_SHARE;
            case COMMENT:
                return FinanceServiceTypeEnum.LIVE_MINING_COMMENT;
            case LIKE:
                return FinanceServiceTypeEnum.LIVE_MINING_LIKE;
            case GIFT:
                return FinanceServiceTypeEnum.LIVE_MINING_GIFT;
        }
        return FinanceServiceTypeEnum.STAR_INCOME_;
    }

    private static void calcLog(LiveMiningRecord record, LiveMiningCalcLog calcLog) {
        LiveMiningTypeEnum instance = LiveMiningTypeEnum.getInstance(record.getType());
        switch (instance) {
            case LIKE:
                calcLog.setLike(calcLog.getLike() + 1);
                calcLog.setLikeAmount(calcLog.getLikeAmount().add(record.getAmount()));
                break;
            case GIFT:
                calcLog.setGift(calcLog.getGift() + 1);
                calcLog.setGiftAmount(calcLog.getGiftAmount().add(record.getAmount()));
                break;
            case VIEW:
                calcLog.setView(calcLog.getView() + 1);
                calcLog.setViewAmount(calcLog.getViewAmount().add(record.getAmount()));
                break;
        }
    }

}
