package com.starwars.script;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static java.util.Arrays.asList;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static springfox.documentation.builders.PathSelectors.regex;
import static springfox.documentation.builders.RequestHandlerSelectors.any;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@SpringBootApplication
@EnableSwagger2
public class StarwarsScriptApplication {

    public static void main(String[] args) {
        SpringApplication.run(StarwarsScriptApplication.class, args);
    }

    private static final String CHARGE_POINTS = "Starwars Script";
    private static final String VERSION = "1.0";

    @Bean
    public Docket documentation() {
        return new Docket(SWAGGER_2)
                .select()
                .apis(any())
                .paths(regex("/api.*"))
                .build()
                .pathMapping("/")
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .globalResponseMessage(GET, apiResponses());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(CHARGE_POINTS)
                .version(VERSION)
                .build();
    }

    private List<ResponseMessage> apiResponses() {
        return asList(
                new ResponseMessageBuilder().code(204).message("No Content").build(),
                new ResponseMessageBuilder().code(400).message("Bad Request").build(),
                new ResponseMessageBuilder().code(422).message("Unprocessable Entity").build(),
                new ResponseMessageBuilder().code(403).message("Forbidden").build(),
                new ResponseMessageBuilder().code(404).message("Invalid request").build(),
                new ResponseMessageBuilder().code(405).message("Method Not Allowed").build(),
                new ResponseMessageBuilder().code(500).message("Internal Server Error").build());
    }


}
