package com.cabbage.GatwayRoute.api;

/**
 * 网关模块的实现类
 * 同时实现多个接口的时候放在第一个才嫩注册到注册中心
 */
public interface RouteService {
    String getRouteDefinitions();
}
