package com.imooc;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Auther: huangqn
 * @Date: 2020/11/22 12:13
 * @Description:
 */
@SpringBootApplication
@MapperScan("com.imooc.mapper")
@ComponentScan(basePackages = {"com.imooc","org.n3r.idworker"})
@EnableScheduling
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class,args);
	}
}
