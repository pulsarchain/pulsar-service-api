package com.bosha.gateway.server.filter;

import com.bosha.gateway.server.config.FilterConstants;
import org.springframework.cloud.gateway.filter.headers.HttpHeadersFilter;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @Author liqingping
 * @Date 2019-04-16 11:28
 */
@Component
public class HeaderFilter implements HttpHeadersFilter, Ordered {

    @Override
    public HttpHeaders filter(HttpHeaders input, ServerWebExchange exchange) {
        LocaleContextHolder.setLocale(exchange.getLocaleContext().getLocale());
        HttpHeaders httpHeaders = new HttpHeaders();
        input.forEach((key, value) -> {
            if (!HttpHeaders.ACCEPT.equalsIgnoreCase(key)) {
                httpHeaders.addAll(key, value);
            }
        });
        httpHeaders.add(HttpHeaders.ACCEPT, MediaType.ALL_VALUE);
        return httpHeaders;
    }

    @Override
    public int getOrder() {
        return FilterConstants.Ordered.HEADER_FILTER;
    }
}
