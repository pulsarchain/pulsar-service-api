package com.bosha.star.server.job;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


import com.bosha.commons.config.EnvConfig;
import com.bosha.commons.third.weixinpush.WeixinPush;
import com.bosha.commons.third.weixinpush.autoconfiguration.WeixinPushProperties;
import com.bosha.star.api.entity.LiveMiningVod;
import com.bosha.star.api.entity.LiveMiningVodConfirm;
import com.bosha.star.server.config.StarServiceConfig;
import com.bosha.star.server.mapper.LiveMiningMapper;
import com.bosha.star.server.mapper.LiveMiningVodConfirmMapper;
import com.bosha.star.server.mapper.LiveMiningVodMapper;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.vod.v20180717.VodClient;
import com.tencentcloudapi.vod.v20180717.models.EditMediaFileInfo;
import com.tencentcloudapi.vod.v20180717.models.EditMediaRequest;
import com.tencentcloudapi.vod.v20180717.models.EditMediaResponse;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: ImportAccountToTencentImJob
 * @Author liqingping
 * @Date 2020-04-03 8:43
 */
@Slf4j
@Component
public class VideoEditJob {

    @Autowired
    private LiveMiningMapper liveMiningMapper;
    @Autowired
    private LiveMiningVodMapper liveMiningVodMapper;
    @Autowired
    private LiveMiningVodConfirmMapper liveMiningVodConfirmMapper;
    @Autowired
    private StarServiceConfig starServiceConfig;
    @Autowired
    private WeixinPushProperties weixinPushProperties;

    @XxlJob("VideoEditJob")//10min
    public ReturnT<String> videoEditJob(String s) {
        try {
            List<Long> ids = liveMiningMapper.unEditVideoIds();
            if (CollectionUtils.isEmpty(ids))
                return ReturnT.SUCCESS;
            for (Long id : ids) {
                List<LiveMiningVod> list = liveMiningVodMapper.listFileId(id);
                if (CollectionUtils.isEmpty(list)) {
                    log.warn("【视频拼接】fileId为空，直播id={}，主播可能未开播过", id);
                    LiveMiningVodConfirm build = LiveMiningVodConfirm.builder().createTime(new Date()).liveMiningId(id).status(4).taskId(id.toString()).build();
                    build.setFileType("");
                    build.setFileId("");
                    build.setVideoUrl("");
                    liveMiningVodConfirmMapper.insertSelective(build);
                    continue;
                }
                LiveMiningVodConfirm build = LiveMiningVodConfirm.builder().createTime(new Date()).liveMiningId(id).status(1).taskId("").build();
                if (list.size() == 1) {
                    LiveMiningVod vod = list.get(0);
                    build.setStatus(2);
                    build.setFileType(vod.getFileFormat());
                    build.setFileId(vod.getFileId());
                    build.setVideoUrl(vod.getVideoUrl());
                    liveMiningVodConfirmMapper.insertSelective(build);
                    log.info("【视频拼接】live={}，只有一个文件，直接添加成功，vod={}", vod.getLiveMiningId(), vod);
                    continue;
                }
                String taskId = edit(list.stream().map(LiveMiningVod::getFileId).collect(Collectors.toList()));
                build.setTaskId(taskId);
                liveMiningVodConfirmMapper.insertSelective(build);
                log.info("【视频拼接】添加edit任务，liveId={}，taskId={}", id, taskId);
            }
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            if (EnvConfig.isProd())
                WeixinPush.push(weixinPushProperties, "【视频拼接】失败，error=" + e.getMessage());
            return ReturnT.FAIL;
        }
    }

    private String edit(List<String> fileIds) throws TencentCloudSDKException {
        try {
            Credential cred = new Credential(starServiceConfig.getLiveConfig().getVideoSecretId(), starServiceConfig.getLiveConfig().getVideoSecretKey());
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("vod.tencentcloudapi.com");
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            VodClient client = new VodClient(cred, starServiceConfig.getLiveConfig().getRegion(), clientProfile);
            EditMediaRequest req = new EditMediaRequest();
            req.setInputType("File");
            EditMediaFileInfo[] infos = new EditMediaFileInfo[fileIds.size()];
            for (int i = 0; i < fileIds.size(); i++) {
                EditMediaFileInfo info = new EditMediaFileInfo();
                info.setFileId(fileIds.get(i));
                infos[i] = info;
            }
            req.setFileInfos(infos);
            EditMediaResponse resp = client.EditMedia(req);
            return resp.getTaskId();
        } catch (TencentCloudSDKException e) {
            log.error(e.getMessage(), e);
            if (EnvConfig.isProd())
                WeixinPush.push(weixinPushProperties, "【视频拼接】失败，error=" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
