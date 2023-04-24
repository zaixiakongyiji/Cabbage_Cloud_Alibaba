package com.cabbage.filter;

import com.cabbage.constants.SecurityConstants;
import com.cabbage.constants.WebConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.annotation.OrderUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
public class AllApiLoggingFilter implements Ordered, GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (log.isDebugEnabled()) {
            String info = String.format("Method:{%s} Host:{%s} Path:{%s} Query:{%s}",
                    exchange.getRequest().getMethod().name(), exchange.getRequest().getURI().getHost(),
                    exchange.getRequest().getURI().getPath(), exchange.getRequest().getQueryParams());
            log.debug(info);
        }
        exchange.getAttributes().put(WebConstants.START_TIME, System.currentTimeMillis());
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            Long startTime = exchange.getAttribute(WebConstants.START_TIME);
            if (startTime != null) {
                Long requestTime = (System.currentTimeMillis() - startTime);
                List<String> ips = exchange.getRequest().getHeaders().get(WebConstants.X_REAL_IP);
                String ip = ips != null ? ips.get(0) : null;
                String api = exchange.getRequest().getURI().getRawPath();

                int code = 500;
                if (exchange.getResponse().getStatusCode() != null) {
                    code = exchange.getResponse().getStatusCode().value();
                }
//                 当前仅记录日志，后续可以添加日志队列，来过滤请求慢的接口
                if (log.isDebugEnabled()) {
                    log.debug("來自IP：{}的請求：{}，響應狀態碼：{}，請求時間：{}ms", ip, api, code, requestTime);
                }
            }
        }));
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
