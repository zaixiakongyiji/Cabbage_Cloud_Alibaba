package com.cabbage.component;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class RedisRouteDefinitionRepository implements RouteDefinitionRepository {

    private Map<Object, RouteDefinition> routeDefinitionMap = new HashMap<>();

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        List<RouteDefinition> routeDefinitions = new ArrayList<>();
        routeDefinitionMap.forEach((k, v) -> {
            routeDefinitions.add(v);
        });
        return Flux.fromIterable(routeDefinitions);
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return route
                .flatMap(routeDefinition -> {
                    log.info("routeDefinition:" + routeDefinition);
                    routeDefinitionMap.put(routeDefinition.getId(), routeDefinition);
                    return Mono.empty();
                });
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return routeId.flatMap(id -> {
            if (routeDefinitionMap.containsKey(id)) {
                routeDefinitionMap.remove(id);
                return Mono.empty();
            }
            return Mono.defer(() -> Mono.error(new NotFoundException("路由文件没有找到: " + routeId)));
        });
    }
}
