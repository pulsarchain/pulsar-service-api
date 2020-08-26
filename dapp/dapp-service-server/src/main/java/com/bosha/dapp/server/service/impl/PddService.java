package com.bosha.dapp.server.service.impl;

import java.util.Collections;
import java.util.List;


import com.alibaba.fastjson.JSON;
import com.bosha.commons.exception.BaseException;
import com.bosha.dapp.api.dto.PddSearchResult;
import com.bosha.dapp.api.dto.PddUrlGenerate;
import com.bosha.dapp.api.enums.DappErrorMsgEnum;
import com.bosha.dapp.server.config.DappServiceConfig;
import com.bosha.dapp.server.redis.CacheKey;
import com.pdd.pop.sdk.http.PopBaseHttpResponse;
import com.pdd.pop.sdk.http.PopClient;
import com.pdd.pop.sdk.http.PopHttpClient;
import com.pdd.pop.sdk.http.api.pop.request.PddDdkGoodsPromotionUrlGenerateRequest;
import com.pdd.pop.sdk.http.api.pop.request.PddDdkGoodsSearchRequest;
import com.pdd.pop.sdk.http.api.pop.response.PddDdkGoodsPromotionUrlGenerateResponse;
import com.pdd.pop.sdk.http.api.pop.response.PddDdkGoodsSearchResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: PddService
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-06-01 11:30
 */
@Service
@Slf4j
public class PddService {

    @Autowired
    private DappServiceConfig dappServiceConfig;
    @Autowired
    private RedissonClient redis;

    public PddSearchResult<PddDdkGoodsSearchResponse.GoodsSearchResponseGoodsListItem> search(String q, Integer page, Integer size) {
        PopClient client = new PopHttpClient(dappServiceConfig.getPddConfig().getClientId(), dappServiceConfig.getPddConfig().getClientSecret());
        PddDdkGoodsSearchRequest request = new PddDdkGoodsSearchRequest();
        request.setPid(dappServiceConfig.getPddConfig().getPid());
        request.setKeyword(q);
        request.setPage(page);
        request.setPageSize(size);
        PddSearchResult<PddDdkGoodsSearchResponse.GoodsSearchResponseGoodsListItem> result = new PddSearchResult<>();
        try {
            PddDdkGoodsSearchResponse response = client.syncInvoke(request);
            PopBaseHttpResponse.ErrorResponse errorResponse = response.getErrorResponse();
            if (errorResponse != null) {
                log.error("【拼多多】搜索 {}", JSON.toJSONString(errorResponse));
                throw new RuntimeException(JSON.toJSONString(errorResponse));
            }
            PddDdkGoodsSearchResponse.GoodsSearchResponse goodsSearchResponse = response.getGoodsSearchResponse();
            String searchId = goodsSearchResponse.getSearchId();
            Integer totalCount = goodsSearchResponse.getTotalCount();
            List<PddDdkGoodsSearchResponse.GoodsSearchResponseGoodsListItem> goodsList = goodsSearchResponse.getGoodsList();
            result.setSearch_id(searchId);
            result.setTotal_count(totalCount);
            result.setGoods_list(goodsList);
            log.debug("【拼多多】搜索：q={}，page={}，size={}，totalCount={}", q, page, size, totalCount);
        } catch (Exception e) {
            log.error("【拼多多】搜索请求失败", e);
            throw new BaseException(DappErrorMsgEnum.GOODS_SEARCH_FAIL);
        }
        return result;
    }

    public PddUrlGenerate generateUrl(Long goodsId, String searchId) {
        RBucket<PddUrlGenerate> bucket = redis.getBucket(CacheKey.Dapp.PDD_URL.key + goodsId);
        if (bucket.isExists())
            return bucket.get();
        PopClient client = new PopHttpClient(dappServiceConfig.getPddConfig().getClientId(), dappServiceConfig.getPddConfig().getClientSecret());
        PddDdkGoodsPromotionUrlGenerateRequest request = new PddDdkGoodsPromotionUrlGenerateRequest();
        request.setPId(dappServiceConfig.getPddConfig().getPid());
        request.setGenerateSchemaUrl(true);
        request.setSearchId(searchId);
        request.setGoodsIdList(Collections.singletonList(goodsId));
        try {
            PddDdkGoodsPromotionUrlGenerateResponse response = client.syncInvoke(request);
            PopBaseHttpResponse.ErrorResponse errorResponse = response.getErrorResponse();
            if (errorResponse != null) {
                log.error("【拼多多】转换链接 {}", JSON.toJSONString(errorResponse));
                throw new RuntimeException(JSON.toJSONString(errorResponse));
            }
            PddDdkGoodsPromotionUrlGenerateResponse.GoodsPromotionUrlGenerateResponse generateResponse = response.getGoodsPromotionUrlGenerateResponse();
            if (CollectionUtils.isEmpty(generateResponse.getGoodsPromotionUrlList()))
                throw new RuntimeException("转换链接返回的数组为空");
            for (PddDdkGoodsPromotionUrlGenerateResponse.GoodsPromotionUrlGenerateResponseGoodsPromotionUrlListItem item : generateResponse.getGoodsPromotionUrlList()) {
                PddUrlGenerate build = PddUrlGenerate.builder().mobile_url(item.getMobileUrl()).schema_url(item.getSchemaUrl()).build();
                bucket.set(build, CacheKey.Dapp.PDD_URL.expireTime, CacheKey.Dapp.PDD_URL.timeUnit);
                return build;
            }
        } catch (Exception e) {
            log.error("【拼多多】转换链接请求失败", e);
            throw new BaseException(DappErrorMsgEnum.GOODS_SEARCH_FAIL);
        }
        return null;
    }


}
