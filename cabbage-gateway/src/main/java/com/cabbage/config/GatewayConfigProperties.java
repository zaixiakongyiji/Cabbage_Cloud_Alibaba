package com.cabbage.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.List;

@Data
@RefreshScope
@ConfigurationProperties("gateway")
public class GatewayConfigProperties {

    private String encodeKey;

    private List<String> ignore;
}
