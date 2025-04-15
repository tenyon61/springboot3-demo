package com.tenyon.web.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Knife4jConfig
 *
 * @author tenyon
 * @date 2024/12/24
 */
@Configuration
public class Knife4jConfig {
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                // 接口文档标题
                .info(new Info().title("Web接口文档API")
                        // 接口文档简介
                        .description("Web项目接口文档")
                        // 接口文档版本
                        .version("1.0.0")
                        .termsOfService("https://tenyon.cn/cc")
                        // 开发者联系方式
                        .contact(new Contact().name("Yovvis")
                                .email("tenyon@cqbo.com")));
    }
}
