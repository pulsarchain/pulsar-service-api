package com.bosha.dapp.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.bosha.commons.exception.BaseException;
import com.bosha.dapp.api.dto.TaoBaoSearchResult;
import com.bosha.dapp.api.enums.DappErrorMsgEnum;
import com.bosha.dapp.server.config.DappServiceConfig;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.internal.util.TaobaoLogger;
import com.taobao.api.request.TbkDgMaterialOptionalRequest;
import com.taobao.api.response.TbkDgMaterialOptionalResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA 2020.1
 *
 * @DESCRIPTION: TaoBaoService
 * @Author liqingping：lqp0817@gmail.com
 * @Date 2020-06-01 8:58
 */
@Service
@Slf4j
public class TaoBaoService {

    static {
        TaobaoLogger.setNeedEnableLogger(false);
    }

    @Autowired
    private DappServiceConfig dappServiceConfig;

    public TaoBaoSearchResult<TbkDgMaterialOptionalResponse.MapData> search(String q, Long page, Long size, int time) {
        TaobaoClient client = new DefaultTaobaoClient("https://eco.taobao.com/router/rest",
                dappServiceConfig.getTaoBaoConfig().getAppKey(), dappServiceConfig.getTaoBaoConfig().getAppSecret());
        TbkDgMaterialOptionalRequest req = new TbkDgMaterialOptionalRequest();
        req.setAdzoneId(dappServiceConfig.getTaoBaoConfig().getAdZoneId());
        req.setPlatform(2L);
        req.setQ(q);
        req.setPageNo(page);
        req.setPageSize(size);
        TaoBaoSearchResult<TbkDgMaterialOptionalResponse.MapData> result = new TaoBaoSearchResult<>();
        try {
            TbkDgMaterialOptionalResponse response = client.execute(req);
            if (response.isSuccess()) {
                log.debug("【淘宝搜索】param={}，totalCount={}", JSON.toJSONString(response.getParams()), response.getTotalResults());
                result.setTotal_results(response.getTotalResults());
                result.setResult_list(response.getResultList());
            } else {
                if ("50001".equals(response.getSubCode()) && time <= 3) {
                    return search(q, page, size, time + 1);
                }
                log.error("【淘宝搜索失败】：{}", JSON.toJSONString(response));
                throw new RuntimeException(JSON.toJSONString(response));
            }
        } catch (Exception e) {
            log.error("【淘宝搜索失败】：", e);
            throw new BaseException(DappErrorMsgEnum.GOODS_SEARCH_FAIL);
        }
        return result;
    }
}
