package com.imooc.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Auther: huangqn
 * @Date: 2020/11/30 15:10
 * @Description: 使用restTemplate
 */
@Configuration
public class WebMvcConfig {
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder){
		return restTemplateBuilder.build();
	}
}
