package com.bosha.gateway.server.handler;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


import com.bosha.commons.config.CommonsConfigProperties;
import com.bosha.commons.constants.CommonConstants;
import com.bosha.commons.enums.ResponseFieldEnum;
import com.bosha.gateway.server.config.FilterConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * Created with IntelliJ IDEA 18.0.1
 *
 * @DESCRIPTION: CustomExceptionHandler
 * @Author liqingping
 * @Date 2019-04-04 14:11
 */
@Slf4j
public class CustomExceptionHandler extends DefaultErrorWebExceptionHandler implements ApplicationContextInitializer, ApplicationContextAware {

    @Getter
    @Setter
    public static ApplicationContext applicationContext;

    public CustomExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties, ErrorProperties errorProperties, ApplicationContext applicationContext) {
        super(errorAttributes, resourceProperties, errorProperties, applicationContext);
    }

    @Override
    protected Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        Map<String, Object> error = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
        request.attributes().put(FilterConstants.Key.FILTER, Boolean.FALSE);
        return ServerResponse
                .status(getHttpStatus(error))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(responseMap(error)))
                .doOnNext((resp) -> {
                    if (!request.uri().getPath().startsWith(CommonConstants.Path.API))
                        return;
                    boolean present = request.remoteAddress().isPresent();
                    String ip = present ? request.remoteAddress().get().getAddress().getHostAddress() : "-";
                    log.error("Exception occurred --> status-[{}],ip=[{}],path-[{}]：\n{}\n", getHttpStatus(error).value(), ip, request.uri().getPath(), detailMessage(error));
                });
    }

    @Override
    protected Mono<ServerResponse> renderErrorView(ServerRequest request) {
        return renderErrorResponse(request);
    }

    private String detailMessage(Map<String, Object> map) {
        Object message = map.get("message");
        Object error = map.get("error");
        return (error == null ? "" : error.toString()) + (message == null ? "" : " - " + message.toString());
    }

    private Map<String, Object> responseMap(Map<String, Object> error) {
        CommonsConfigProperties config = applicationContext.getBean(CommonsConfigProperties.class);
        Map<String, Object> map = new HashMap<>();
        Integer status = (Integer) error.get("status");
        map.put(ResponseFieldEnum.code.name(), status);
        switch (HttpStatus.resolve(status)) {
            case NOT_FOUND:
                map.put(ResponseFieldEnum.msg.name(), error.get(ResponseFieldEnum.error.name()) + "-" + error.get("path"));
                break;
            case INTERNAL_SERVER_ERROR:
                map.put(ResponseFieldEnum.msg.name(), LocaleContextHolder.getLocale().equals(Locale.CHINA) ? config.getCnTip() : config.getEnTip());
                break;
            default:
                map.put(ResponseFieldEnum.msg.name(), error.get(ResponseFieldEnum.error.name()));
        }
        if (config.isShowError())
            map.put(ResponseFieldEnum.error.name(), detailMessage(error));
        return map;
    }


    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        CustomExceptionHandler.applicationContext = applicationContext;
    }
}
