package com.example.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * Swagger配置类
 *
 * @author sunxiaoming
 * @date 2026-02-20
 */
@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfig {

    @Bean(value = "gatewayApi")
    public Docket gatewayApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("Gateway API")
                        .description("# 网关服务API接口文档\n\n提供用户登录等认证相关接口")
                        .termsOfServiceUrl("https://doc.example.com/")
                        .version("1.0.0")
                        .build())
                .groupName("网关服务")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.gateway.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}

