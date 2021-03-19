package com.example.awssns.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket commonApi() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("AWS SNS Publisher")
                .description("SNS 서비스 퍼블리셔 앱의 API 설명")
                .version("1.0")
                .build();

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("exmaple")
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors
                .basePackage("com.example.awssns.controller"))
                .paths(PathSelectors.ant("/**"))
                .build();

    }
}
