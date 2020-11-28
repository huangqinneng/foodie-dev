package com.imooc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @Auther: huangqn
 * @Date: 2020/11/23 16:39
 * @Description: 跨域配置
 */
@Configuration
public class CorsConfig {

	public CorsConfig() {
	}

	@Bean
	public CorsFilter corsFilter(){
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		// 设置前端跨域问题 * -- 表示所有ip都可以访问
		corsConfiguration.addAllowedOrigin("http://localhost:8089");
		// 设置是否发生cookie信息
		corsConfiguration.setAllowCredentials(true);
		// 设置允许请求的方式
		corsConfiguration.addAllowedMethod("*");
		// 设置允许请求的header
		corsConfiguration.addAllowedHeader("*");
		// 为url添加映射路径
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		// 表示所有请求都可以映射
		source.registerCorsConfiguration("/**",corsConfiguration);
		return new CorsFilter(source);
	}
}
