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
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                // 接口文档标题
                .info(new Info().title("Web接口文档API")
                        .version("1.0.0")
                        .description("Web项目接口文档")
                        .termsOfService("https://tenyon.cn/cc")
                        .contact(new Contact().name("tenyon")
                                .email("tenyon@cqbo.com")));
    }
}
