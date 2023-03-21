package com.cabbage.config.filter;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.GatewayPredicate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

@Component
public class TokenRoutePredicateFactory extends AbstractRoutePredicateFactory<TokenRoutePredicateFactory.Config> {

//    @Autowired
//    StringRedisTemplate redisTemplate;

    /**
     * Header key.
     */
    public static final String HEADER_KEY = "header";

    /**
     * Regexp key.
     */
    public static final String REGEXP_KEY = "regexp";

    public TokenRoutePredicateFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(HEADER_KEY, REGEXP_KEY);
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        boolean hasRegex = !ObjectUtils.isEmpty(config.regexp);

        return new GatewayPredicate() {
            @Override
            public boolean test(ServerWebExchange exchange) {
                //从请求头中取出Token
                List<String> values = exchange.getRequest().getHeaders().getOrDefault(config.header,
                        Collections.emptyList());

                if (values.isEmpty()) {
                    return false;
                }
                if (hasRegex) {

                    // check if a header value matches
                    for (int i = 0; i < values.size(); i++) {
                        String value = values.get(i);
                        if (value.matches(config.regexp)) {
                            return true;
                        }
                    }
                    return false;
                }

                // there is a value and since regexp is empty, we only check existence.
                return true;
            }

            @Override
            public Object getConfig() {
                return config;
            }

            @Override
            public String toString() {
                return String.format("Header: %s regexp=%s", config.header, config.regexp);
            }
        };
    }

    @Validated
    public static class Config {

        @NotEmpty
        private String header;

        private String regexp;

        public String getHeader() {
            return header;
        }

        public Config setHeader(String header) {
            this.header = header;
            return this;
        }

        public String getRegexp() {
        //根据用户名从redis里面拿出来的token
            return regexp;
        }

        public Config setRegexp(String regexp) {
            this.regexp = regexp;
            return this;
        }

    }
}
