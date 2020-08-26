package com.bosha.gateway.server.filter;

import java.nio.charset.StandardCharsets;
import java.util.Set;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bosha.commons.constants.CommonConstants;
import com.bosha.commons.dto.RequestInfo;
import com.bosha.commons.enums.ResponseFieldEnum;
import com.bosha.commons.enums.UserTypeEnum;
import com.bosha.gateway.server.config.FilterConstants;
import com.bosha.gateway.server.config.GatewayConfig;
import com.bosha.gateway.server.config.RedisKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RMap;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: ManagerAccessFilter
 * @Author liqingping
 * @Date 2019-04-07 11:50
 */
@Component
@Slf4j
public class ManagerAccessFilter implements GlobalFilter, Ordered {

    private static final PathMatcher PATH_MATCHER = new AntPathMatcher();

    private static final String MANAGER_API_PATTERN = "/api/*" + CommonConstants.Path.MANAGER + "/**";

    @Autowired
    private RedissonClient redis;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        if (!PATH_MATCHER.match(MANAGER_API_PATTERN, path))
            return chain.filter(exchange);

        if (Boolean.TRUE.equals(exchange.getAttribute(FilterConstants.Key.IGNORE)))
            return chain.filter(exchange);

        RequestInfo requestInfo = exchange.getAttribute(CommonConstants.REQUEST_INFO);
        assert requestInfo != null;
        if (requestInfo.getUserType() != UserTypeEnum.MANAGER) {
            return fail(exchange, HttpStatus.UNAUTHORIZED.value(), "token invalid,you don't have permission to access this api", requestInfo, HttpStatus.UNAUTHORIZED);
        }

        RMap<Long, String> statusMap = redis.getMap(RedisKey.AdminUser.STATUS_DISABLED_MAP);
        if (statusMap.containsKey(requestInfo.getUserId())) {
            return fail(exchange, 1001, "您的账号已被禁用", requestInfo, HttpStatus.OK);
        }

        RMap<Long, String> resetPasswordMap = redis.getMap(RedisKey.AdminUser.RESET_PASSWORD_MAP);
        if (resetPasswordMap.containsKey(requestInfo.getUserId())) {
            return fail(exchange, 1001, "您的账号密码已被重置，请重新登录", requestInfo, HttpStatus.OK);
        }

        RMapCache<Long, String> map = redis.getMapCache(RedisKey.AdminUser.LOGIN_MAP);
        if (!map.isExists() || StringUtils.isBlank(map.get(requestInfo.getUserId()))) {
            return fail(exchange, 1001, "登录信息已失效，请重新登录", requestInfo, HttpStatus.OK);
        }
        if (!StringUtils.equals(map.get(requestInfo.getUserId()), requestInfo.getDeviceId())) {
            return fail(exchange, 1001, "您的账号已在其他地方登录", requestInfo, HttpStatus.OK);
        }

        return chain.filter(exchange);
    }


    @Override
    public int getOrder() {
        return FilterConstants.Ordered.MANAGER_ACCESS_FILTER;
    }

    private Mono<Void> fail(ServerWebExchange exchange, int code, String msg, RequestInfo requestInfo, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ResponseFieldEnum.code.name(), code);
        jsonObject.put(ResponseFieldEnum.msg.name(), msg);
        response.setStatusCode(httpStatus);
        log.error("Access Denied： ip=[{}]，userId=[{}]，requestURI=[{}]，reason=[{}]", requestInfo.getIp(), requestInfo.getUserId(), requestInfo.getOriginReqUrl(), msg);
        DataBuffer buffer = response.bufferFactory().wrap(JSON.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue).getBytes(StandardCharsets.UTF_8));
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        exchange.getAttributes().put(FilterConstants.Key.FILTER, Boolean.FALSE);
        return response.writeWith(Mono.just(buffer));
    }
}
