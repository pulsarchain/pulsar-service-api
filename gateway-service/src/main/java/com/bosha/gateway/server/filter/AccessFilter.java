package com.bosha.gateway.server.filter;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bosha.commons.constants.CommonConstants;
import com.bosha.commons.dto.RequestInfo;
import com.bosha.commons.enums.ResponseFieldEnum;
import com.bosha.commons.enums.UserClientTypeEnum;
import com.bosha.commons.enums.UserTypeEnum;
import com.bosha.commons.utils.JWTUtil;
import com.bosha.commons.utils.UUIDUtils;
import com.bosha.gateway.server.config.FilterConstants;
import com.bosha.gateway.server.config.GatewayConfig;
import com.bosha.gateway.server.utils.IPUtils;
import com.bosha.gateway.server.utils.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/**
 * @Description
 * @Author liqingping
 * @Date 2019-4-6 006 14:15
 */
@Component
@Slf4j
public class AccessFilter implements GlobalFilter, Ordered {

    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private GatewayConfig gatewayConfig;

    @Override
    public int getOrder() {
        return FilterConstants.Ordered.ACCESS_FILTER;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        ServerWebExchange.Builder mutate = exchange.mutate();

        String token = request.getHeaders().getFirst(CommonConstants.TOKEN);
        String version = request.getHeaders().getFirst("version");
        String clientType = request.getHeaders().getFirst("clientType");
        String deviceId = request.getHeaders().getFirst("deviceId");

        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setDeviceId(deviceId);
        requestInfo.setOriginReqUrl(path);
        requestInfo.setIp(IPUtils.getClientIp(request));
        requestInfo.setReqId(UUIDUtils.getUUIDShort());
        MDC.put(FilterConstants.Key.REQ_ID, requestInfo.getReqId());
        exchange.getAttributes().put(FilterConstants.Key.REQ_ID, requestInfo.getReqId());

        if (StringUtils.isNotBlank(clientType) && StringUtils.isNumeric(clientType))
            requestInfo.setClientType(UserClientTypeEnum.clientType(Integer.parseInt(clientType)));
        if (StringUtils.isNotBlank(version))
            requestInfo.setVersionStr(version);

        //传了token就必须校验，避免后续业务出现异常
        if (StringUtils.isNotBlank(token)) {
            try {
                Long userId = JWTUtil.getUserId(token);
                requestInfo.setDeviceId(JWTUtil.getDeviceId(token));
                requestInfo.setAddress(JWTUtil.getAddress(token));
                String adminUserName = JWTUtil.getAdminUserName(token);
                if (StringUtils.isNotBlank(adminUserName))
                    try {
                        requestInfo.setAdminUserName(URLEncoder.encode(adminUserName, StandardCharsets.UTF_8.name()));
                    } catch (Exception e) {
                    }
                UserTypeEnum userType = JWTUtil.getUserType(token);
                requestInfo.setUserType(userType);
                requestInfo.setUserId(userId);
                if (StringUtils.isBlank(version))
                    requestInfo.setVersionStr(JWTUtil.getVersion(token));
                if (StringUtils.isBlank(clientType) && userType.getType() > 1)
                    requestInfo.setClientType(JWTUtil.getUserClientType(token));
            } catch (Exception e) {
                if (!ignore(path, request)) {
                    log.error("解析token失败 ：{}，{}", token, e.getMessage());
                    return tokenFail(exchange, requestInfo);
                }
            }
        }

        exchange = mutate.request(request.mutate().header(CommonConstants.REQUEST_INFO, new String[]{JSON.toJSONString(requestInfo)}).build()).build();
        exchange.getAttributes().put(CommonConstants.REQUEST_INFO, requestInfo);
        exchange.getAttributes().put(FilterConstants.Key.REQUEST_START_TIME, System.currentTimeMillis());

        //忽略token的直接放行
        boolean ignore = ignore(path, request);
        if (ignore) {
            exchange.getAttributes().put(FilterConstants.Key.IGNORE, Boolean.TRUE);
            return chain.filter(exchange);
        }

        if (StringUtils.isBlank(token) || !jwtUtil.verify(token))
            return tokenFail(exchange, requestInfo);

        return chain.filter(exchange);
    }


    boolean ignore(String path, ServerHttpRequest request) {
        path = path.replaceAll("/", "");
        Map<String, String> map = gatewayConfig.getNoAuthUrlMap();
        if (!map.containsKey(path)) {
            return false;
        }
        if (Boolean.parseBoolean(map.get(path))) {
            boolean b = verifySignature(request.getHeaders());
            if (!b) {
                log.error("签名校验失败。。。");
            }
            return b;
        }
        return true;
    }

    private Mono<Void> tokenFail(ServerWebExchange exchange, RequestInfo requestInfo) {
        ServerHttpResponse response = exchange.getResponse();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ResponseFieldEnum.code.name(), HttpStatus.UNAUTHORIZED.value());
        jsonObject.put(ResponseFieldEnum.msg.name(), "token invalid");
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        log.error("token {}： ip=[{}]，url=[{}]", "验证失败", requestInfo.getIp(), requestInfo.getOriginReqUrl());
        DataBuffer buffer = response.bufferFactory().wrap(JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue).getBytes(StandardCharsets.UTF_8));
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        exchange.getAttributes().put(FilterConstants.Key.FILTER, Boolean.FALSE);
        return response.writeWith(Mono.just(buffer));
    }

    public boolean verifySignature(HttpHeaders httpHeaders) {
        try {
            String signature = httpHeaders.getFirst("signature");
            String timestamp = httpHeaders.getFirst("timestamp");
            String nonce = httpHeaders.getFirst("nonce");
            GatewayConfig.Sign sign = gatewayConfig.getSign();
            if (!sign.isVerifySignature())
                return true;
            if (StringUtils.isAnyBlank(timestamp, signature, nonce) || !StringUtils.isNumeric(timestamp))
                return false;
            boolean check = SignUtil.check(sign.getSecretKey(), signature, timestamp, nonce);
            if (!check)
                return false;
            long diff = System.currentTimeMillis() - Long.parseLong(timestamp);
            return diff <= sign.getExpireMilliseconds();
        } catch (Exception e) {
            log.error("", e);
            return false;
        }
    }
}