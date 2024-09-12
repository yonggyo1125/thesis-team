package org.choongang.global.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(info=@Info(title="논문 API", description = "논문업로드, 다운로드, 삭제, 조회 기능 제공"))
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi apiGroup() {
        return GroupedOpenApi.builder()
                .group("논문 API v1")
                .pathsToMatch("/**")
                .build();
    }
}