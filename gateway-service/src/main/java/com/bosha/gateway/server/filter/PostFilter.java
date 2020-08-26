package com.bosha.gateway.server.filter;

import java.nio.charset.StandardCharsets;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bosha.commons.config.CommonsConfigProperties;
import com.bosha.commons.constants.CommonConstants;
import com.bosha.commons.dto.RequestInfo;
import com.bosha.commons.enums.ResponseFieldEnum;
import com.bosha.commons.enums.UserClientTypeEnum;
import com.bosha.commons.utils.StrUtils;
import com.bosha.gateway.server.config.FilterConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.reactivestreams.Publisher;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @Description
 * @Author liqingping
 * @Date 2019-4-6 006 14:15
 */
@Component
@Slf4j
public class PostFilter implements GlobalFilter, Ordered {

    @Autowired
    private CommonsConfigProperties commonsConfigProperties;

    @Override
    public int getOrder() {
        return FilterConstants.Ordered.POST_FILTER;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Object attribute = exchange.getAttribute(CommonConstants.REQUEST_INFO);
        RequestInfo info = attribute instanceof RequestInfo ? (RequestInfo) attribute : new RequestInfo();
        exchange.getResponse().getHeaders().set(FilterConstants.Key.REQ_ID, info.getReqId());

        DataBufferFactory bufferFactory = exchange.getResponse().bufferFactory();
        ServerHttpResponseDecorator responseDecorator = new ServerHttpResponseDecorator(exchange.getResponse()) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                if (Boolean.FALSE.equals(exchange.getAttribute(FilterConstants.Key.FILTER)))
                    return getDelegate().writeWith(body);

                ServerHttpResponse response = exchange.getResponse();
                HttpStatus statusCode = response.getStatusCode();

                if (statusCode.is3xxRedirection())
                    return getDelegate().writeWith(body);

                Object request_start_time = exchange.getAttribute(FilterConstants.Key.REQUEST_START_TIME);
                long time = request_start_time instanceof Long ? (long) request_start_time : 0;

                if (mdcNotExits()) {
                    MDC.put(FilterConstants.Key.REQ_ID, info.getReqId());
                }
                if (statusCode != HttpStatus.OK || response.getHeaders().containsKey(ResponseFieldEnum.success.name())) {
                    return super.writeWith(Flux.from(body).map(dataBuffer -> {
                        byte[] content = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(content);
                        //释放掉内存
                        DataBufferUtils.release(dataBuffer);
                        String s = new String(content, StandardCharsets.UTF_8);
                        log.error("ip[{}],addr[{}],c[{}],v[{}],lang[{}],path[{}],cost[ {} ms]\nerror=[{}]",
                                info.getIp(),
                                info.getAddress() == null ? "-" : StrUtils.addr(info.getAddress()),
                                info.getClientType() == null || info.getClientType() == UserClientTypeEnum.Unknown ? "-" : info.getClientType(),
                                StringUtils.isBlank(info.getVersionStr()) ? "-" : info.getVersionStr(),
                                LocaleContextHolder.getLocale(), info.getOriginReqUrl(),
                                time == 0 ? "-" : System.currentTimeMillis() - time, s);
                        if (!commonsConfigProperties.isShowError()) {
                            try {
                                JSONObject jsonObject = JSON.parseObject(s);
                                jsonObject.remove("error");
                                content = jsonObject.toJSONString().getBytes(StandardCharsets.UTF_8);
                            } catch (Exception e) {
                            }
                        }
                        return bufferFactory.wrap(content);
                    }));
                }
                log.info("ip[{}],addr[{}],c[{}],v[{}],lang[{}],path[{}],cost[ {} ms]",
                        info.getIp(),
                        info.getAddress() == null ? "-" : StrUtils.addr(info.getAddress()),
                        info.getClientType() == null || info.getClientType() == UserClientTypeEnum.Unknown ? "-" : info.getClientType(),
                        StringUtils.isBlank(info.getVersionStr()) ? "-" : info.getVersionStr(),
                        LocaleContextHolder.getLocale(), info.getOriginReqUrl(),
                        time == 0 ? "-" : System.currentTimeMillis() - time);
                return super.writeWith(body);
            }

            @Override
            public Mono<Void> writeAndFlushWith(Publisher<? extends Publisher<? extends DataBuffer>> body) {
                return writeWith(Flux.from(body).flatMapSequential(p -> p));
            }
        };
        return chain.filter(exchange.mutate().response(responseDecorator).build());
    }

    private boolean mdcNotExits() {
        try {
            return MDC.get(FilterConstants.Key.REQ_ID) == null;
        } catch (Exception e) {
            return false;
        }
    }
}