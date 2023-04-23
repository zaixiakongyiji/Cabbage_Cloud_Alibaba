package com.cabbage.component;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cabbage.dubbo.*;
import com.cabbage.entity.route.*;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@DubboService
public class DynamicRouteServiceHander implements ApplicationEventPublisherAware, CommandLineRunner {

    @DubboReference
    GatewayFilterService filterService;
    @DubboReference
    GatewayRouteService routeService;
    @DubboReference
    GatewayPredicateService predicateService;
    ApplicationEventPublisher publisher;
    @DubboReference
    PredicateTypeService predicateTypeService;

    @DubboReference
    FilterTypeService filterTypeService;
    @Autowired
    RedisRouteDefinitionRepository routeDefinitionWriter;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    /**
     * 启动的时候运行这个方法
     *
     * @param args incoming main method arguments
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        this.getRouteDefinitions();
    }

    @Override
    public String getRouteDefinitions() {
        List<GatewayRoute> routes = routeService.list();
        List<PredicateType> predicateTypes = predicateTypeService.list();
        List<FilterType> filterTypes = filterTypeService.list();

        routes.forEach(route -> {
            RouteDefinition definition = new RouteDefinition();
            definition.setId(route.getName());
            definition.setUri(URI.create(route.getUri()));
            List<GatewayPredicate> predicates = predicateService.list(Wrappers.<GatewayPredicate>lambdaQuery()
                    .eq(GatewayPredicate::getRid, route.getId()));
            if (StringUtils.checkValNotNull(predicates)) {
                Map<String, String> predicateParams = new HashMap<>(4);
                List<PredicateDefinition> predicateList = new ArrayList<>();
                predicates.forEach(p -> {
                    PredicateDefinition predicate = new PredicateDefinition();
                    predicateTypes.forEach(pt -> {
                        predicate.setName(p.getName());
                        if (pt.getName().equals(p.getName())) {
                            predicateParams.put(pt.getNameKeyOne(), p.getParamsOne());
                            if (StringUtils.checkValNotNull(p.getParamsTwo())) {
                                predicateParams.put(pt.getNameKeyTwo(), p.getParamsTwo());
                            }
                        }
                    });
                    predicate.setArgs(predicateParams);
                    predicateList.add(predicate);
                });
                definition.setPredicates(predicateList);
            }
            List<GatewayFilter> filters = filterService.list(Wrappers.<GatewayFilter>lambdaQuery().eq(
                    GatewayFilter::getRid, route.getId()));
            if (StringUtils.checkValNotNull(filters)) {
                Map<String, String> filterParams = new HashMap<>(8);
                List<FilterDefinition> filterList = new ArrayList<>();
                filters.forEach(f -> {
                    FilterDefinition filter = new FilterDefinition();
                    filterTypes.forEach(ft -> {
                        filter.setName(f.getName());
                        if (ft.getName().equals(f.getName())) {
                            filterParams.put(ft.getNameKeyOne(), f.getParamOne());
                            if (StringUtils.checkValNotNull(f.getParamTwo())) {
                                filterParams.put(ft.getNameKeyTwo(), f.getParamTwo());
                            }
                            if (StringUtils.checkValNotNull(f.getParamThree())) {
                                filterParams.put(ft.getNameKeyThree(), f.getParamThree());
                            }
                            if (StringUtils.checkValNotNull(f.getParamFour())) {
                                filterParams.put(ft.getNameKeyFour(), f.getParamFour());
                            }
                            if (StringUtils.checkValNotNull(f.getParamFive())) {
                                filterParams.put(ft.getNameKeyFive(), f.getParamFive());
                            }
                            if (StringUtils.checkValNotNull(f.getParamSix())) {
                                filterParams.put(ft.getNameKeySix(), f.getParamSix());
                            }
                            if (StringUtils.checkValNotNull(f.getParamSeven())) {
                                filterParams.put(ft.getNameKeySeven(), f.getParamSeven());
                            }
                        }
                    });
                    filter.setArgs(filterParams);
                    filterList.add(filter);
                });
                definition.setFilters(filterList);
            }
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            /**
             * 通知GatewayAutoConfiguration里面的routeRefreshListener自己去刷新路由
             * 原来 new RefreshRoutesEvent(new RefreshRoutesEvent( xx)) 里面装的是 this 不知道为啥报空指针
             * 但是这个里面的object好像不是很重要 我就搞了这么个在里面
             */
            publisher.publishEvent(new RefreshRoutesEvent("刷新路由"));
        });
        return "success";
    }

}
