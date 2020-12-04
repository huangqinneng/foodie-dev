package com.imooc;


import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @Auther: huangqn
 * @Date: 2020/11/22 12:13
 * @Description:  war包启动类
 */
public class WarStarterApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// 指向application 这个springboot启动类
		return builder.sources(Application.class);
	}
}
