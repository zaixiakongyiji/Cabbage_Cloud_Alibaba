package com.cabbage.filter;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.http.HttpUtil;
import com.cabbage.config.GatewayConfigProperties;
import com.cabbage.constants.SecurityConstants;
import com.cabbage.constants.WebConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.function.Function;


/**
 * @author lengleng
 * @date 2019 /2/1 密码解密工具类
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class DecoderFilter extends AbstractGatewayFilterFactory {

    private static final List<HttpMessageReader<?>> messageReaders = HandlerStrategies.withDefaults().messageReaders();

    private final GatewayConfigProperties gatewayConfig;

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();
            // 1. 不是登录请求，直接向下执行
            if (!StringUtils.containsAnyIgnoreCase(path, SecurityConstants.OAUTH_LOGIN_URL)) {
                return chain.filter(exchange);
            }
            // 2. 刷新token类型，直接向下执行
            String grantType = request.getQueryParams().getFirst("grant_type");
            if (SecurityConstants.REFRESH_TOKEN.equals(grantType)) {
                return chain.filter(exchange);
            }
            // 3. 前端加密密文解密逻辑
            Class inClass = String.class;
            Class outClass = String.class;
            ServerRequest serverRequest = ServerRequest.create(exchange, messageReaders);

            // 4. 解密生成新的报文
            Mono<?> modifiedBody = serverRequest.bodyToMono(inClass).flatMap(decryptAES());

            BodyInserter bodyInserter = BodyInserters.fromPublisher(modifiedBody, outClass);
            HttpHeaders headers = new HttpHeaders();
            headers.putAll(exchange.getRequest().getHeaders());
            headers.remove(HttpHeaders.CONTENT_LENGTH);

            headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
            CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, headers);
            return bodyInserter.insert(outputMessage, new BodyInserterContext()).then(Mono.defer(() -> {
                ServerHttpRequest decorator = decorate(exchange, headers, outputMessage);
                return chain.filter(exchange.mutate().request(decorator).build());
            }));
        };
    }

    /**
     * 原文解密
     * @return
     */
    private Function decryptAES() {
        return s -> {
            // 构建前端对应解密AES 因子
            AES aes = new AES(Mode.CFB, Padding.NoPadding,
                    new SecretKeySpec(gatewayConfig.getEncodeKey().getBytes(), SecurityConstants.ASE),
                    new IvParameterSpec(gatewayConfig.getEncodeKey().getBytes()));

            // 获取请求密码并解密
            Map<String, String> inParamsMap = HttpUtil.decodeParamMap((String) s, CharsetUtil.CHARSET_UTF_8);
            if (inParamsMap.containsKey(SecurityConstants.PASSWORD)) {
                String password = aes.decryptStr(inParamsMap.get(SecurityConstants.PASSWORD));
                // 返回修改后报文字符
                inParamsMap.put(SecurityConstants.PASSWORD, password);
            }
            else {
                log.error("非法请求数据:{}", s);
            }
            return Mono.just(HttpUtil.toParams(inParamsMap, Charset.defaultCharset(), true));
        };
    }

    /**
     * 报文转换
     * @return
     */
    private ServerHttpRequestDecorator decorate(ServerWebExchange exchange, HttpHeaders headers,
                                                CachedBodyOutputMessage outputMessage) {
        return new ServerHttpRequestDecorator(exchange.getRequest()) {
            @Override
            public HttpHeaders getHeaders() {
                long contentLength = headers.getContentLength();
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.putAll(super.getHeaders());
                if (contentLength > 0) {
                    httpHeaders.setContentLength(contentLength);
                }
                else {
                    httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
                }
                return httpHeaders;
            }

            @Override
            public Flux<DataBuffer> getBody() {
                return outputMessage.getBody();
            }
        };
    }

}

