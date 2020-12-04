package com.imooc.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Auther: huangqn
 * @Date: 2020/11/30 15:10
 * @Description: 使用restTemplate
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder){

		return restTemplateBuilder.build();
	}

	/**
	 * 实现静态资源映射
	 * @param registry
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**")
				.addResourceLocations("classpath:/META-INF/resources/")
//				.addResourceLocations("file:/D:\\project\\foodie\\foodie-dev\\foodie-dev-api\\src\\main\\resources\\images\\")
				.addResourceLocations("file:/home/huangqn/tomcat/foodie-tomcat/images/");
	}
}
