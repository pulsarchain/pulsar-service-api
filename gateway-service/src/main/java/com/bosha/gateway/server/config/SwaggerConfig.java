package com.bosha.gateway.server.config;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;


import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import cn.hutool.http.HttpUtil;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.io.Resource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.MultiValueMap;
import org.springframework.util.PathMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;

/**
 * Created with IntelliJ IDEA 19.0.1
 *
 * @DESCRIPTION: SwaggerConfig
 * @Author liqingping
 * @Date 2019-04-30 15:01
 */
@Api(tags = "【Pulsar】微服务swagger地址汇总")
@Slf4j
@RestController
@Profile(value = {"dev", "test"})
public class SwaggerConfig {

    private static Map<String, String> map = new HashMap<>(16);

    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private GatewayProperties gatewayProperties;

    @Bean
    public RouterFunction<ServerResponse> doc(@Value("classpath:/META-INF/resources/doc.html") final Resource resource) {
        return route(GET("/doc.html"), request -> ok().contentType(MediaType.TEXT_HTML).syncBody(resource));
    }

    @Component
    @Profile(value = {"dev", "test"})
    public class Test implements GlobalFilter, Ordered {

        private final PathMatcher PATH_MATCHER = new AntPathMatcher();

        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();
            if (PATH_MATCHER.match("/api/**/v2/api-docs", path)) {
                MultiValueMap<String, String> queryParams = request.getQueryParams();
                String service = queryParams.getFirst("service");
                List<ServiceInstance> instances = discoveryClient.getInstances(service);
                if (CollectionUtils.isEmpty(instances))
                    return chain.filter(exchange);
                ServiceInstance serviceInstance = instances.get(0);
                String host = serviceInstance.getHost();
                int port = serviceInstance.getPort();
                String result = HttpUtil.get("http://" + host + ":" + port + "/v2/api-docs");
                ServerHttpResponse response = exchange.getResponse();
                DataBuffer buffer = response.bufferFactory().wrap(result.getBytes(StandardCharsets.UTF_8));
                response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
                exchange.getAttributes().put(FilterConstants.Key.FILTER, Boolean.FALSE);
                return response.writeWith(Mono.just(buffer));
            }
            return chain.filter(exchange);
        }

        @Override
        public int getOrder() {
            return FilterConstants.Ordered.HEADER_FILTER - 1;
        }
    }

    @RestController
    @RequestMapping("/swagger-resources")
    public class SwaggerHandler {

        @Autowired(required = false)
        private SecurityConfiguration securityConfiguration;
        @Autowired(required = false)
        private UiConfiguration uiConfiguration;
        @Autowired
        private SwaggerResourcesProvider swaggerResources;

        @GetMapping("/configuration/security")
        public Mono<ResponseEntity<SecurityConfiguration>> securityConfiguration() {
            return Mono.just(new ResponseEntity<>(Optional.ofNullable(securityConfiguration).orElse(SecurityConfigurationBuilder.builder().build()), HttpStatus.OK));
        }

        @GetMapping("/configuration/ui")
        public Mono<ResponseEntity<UiConfiguration>> uiConfiguration() {
            return Mono.just(new ResponseEntity<>(Optional.ofNullable(uiConfiguration).orElse(UiConfigurationBuilder.builder().build()), HttpStatus.OK));
        }

        @GetMapping
        public Mono<ResponseEntity> swaggerResources() {
            return Mono.just((new ResponseEntity<>(swaggerResources.get(), HttpStatus.OK)));
        }
    }

    @Component
    @Primary
    @AllArgsConstructor
    public class SwaggerResourceConfig implements SwaggerResourcesProvider {

        private final GatewayProperties gatewayProperties;

        @Override
        public List<SwaggerResource> get() {
            List<RouteDefinition> routes = gatewayProperties.getRoutes();
            routes.forEach(rd -> {
                String host = rd.getUri().getHost();
                Map<String, String> args = rd.getPredicates().stream().findFirst().get().getArgs();
                String value = args.get(args.keySet().stream().findFirst().get());
                map.put(host, value);
            });

            List<SwaggerResource> resources = new ArrayList<>();
            List<String> services = discoveryClient.getServices();
            services.forEach(s -> {
                List<ServiceInstance> instances = discoveryClient.getInstances(s);
                instances.forEach(instance -> {
                    if (instance.getPort() < 8010)
                        return;
                    String serviceId = instance.getServiceId();
                    String path = map.getOrDefault(serviceId, "");
                    resources.add(swaggerResource(instance.getServiceId() + "-" + instance.getHost(),
                            path.replace("**", "v2/api-docs?service=") + instance.getServiceId(), instance.getPort()));
                });
            });
            resources.sort(Comparator.comparingInt(o -> Integer.parseInt(o.getSwaggerVersion())));
            return resources;
        }

        private SwaggerResource swaggerResource(String name, String location, int port) {
            SwaggerResource swaggerResource = new SwaggerResource();
            swaggerResource.setName(name);
            swaggerResource.setLocation(location);
            swaggerResource.setSwaggerVersion(String.valueOf(port));
            return swaggerResource;
        }
    }
}
