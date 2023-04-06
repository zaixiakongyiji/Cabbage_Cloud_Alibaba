package com.cabbage.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.List;

@Configuration
//@EnableSwagger2WebFlux
@EnableKnife4j
public class SwaggerConfig {

//    @Autowired
//    Environment env;

    @Value("${spring.application.name}")
    String name;

    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName(name)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.cabbage"))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    private List<ApiKey> apiKeys() {
        List<ApiKey> apiKeys = new ArrayList<>();
        ApiKey token = new ApiKey("Authorization", "accessToken", "header");
        apiKeys.add(token);
        return apiKeys;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(name + " APIs")
                .description("# " + name + " APIs")
//                .termsOfServiceUrl("http://www.xx.com/")
//                .contact("xx@qq.com")
                .version("1.0")
                .build();
    }

}
