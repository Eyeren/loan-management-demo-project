package com.customerloan.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
	private static final String TITLE_INFO ="Customer Loan API";
	private static final String VERSION_INFO ="2.6.0";
	private static final String TITLE_DESCRIPTION ="Spring Doc Swagger Description";

	@Bean
	protected OpenAPI customOpenAPI() {
		return new OpenAPI()
                .info(new Info()
                        .title(TITLE_INFO)
                        .version(VERSION_INFO)
                        .description(TITLE_DESCRIPTION));
    }
    
}