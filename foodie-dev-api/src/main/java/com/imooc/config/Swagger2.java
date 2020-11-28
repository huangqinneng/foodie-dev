package com.imooc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: huangqn
 * @Date: 2020/11/23 13:56
 * @Description: 接口文档配置
 */
@Configuration
@EnableSwagger2
public class Swagger2 {

	// http://localhost:8088/swagger-ui.html
	// http://localhost:8088/doc.html
	// 配置swagger2核心配置 docket
	@Bean
	public Docket createRestApi(){
		List<ResponseMessage> responseMessageList = new ArrayList<>();
		responseMessageList.add(new ResponseMessageBuilder().code(404).message("找不到资源").responseModel(new ModelRef("ApiError")).build());
		responseMessageList.add(new ResponseMessageBuilder().code(409).message("业务逻辑异常").responseModel(new ModelRef("ApiError")).build());
		responseMessageList.add(new ResponseMessageBuilder().code(422).message("参数校验异常").responseModel(new ModelRef("ApiError")).build());
		responseMessageList.add(new ResponseMessageBuilder().code(500).message("服务器内部错误").responseModel(new ModelRef("ApiError")).build());
		responseMessageList.add(new ResponseMessageBuilder().code(503).message("Hystrix异常").responseModel(new ModelRef("ApiError")).build());

		return new Docket(DocumentationType.SWAGGER_2)
				.globalResponseMessage(RequestMethod.GET, responseMessageList)
				.globalResponseMessage(RequestMethod.POST, responseMessageList)
				.globalResponseMessage(RequestMethod.PUT, responseMessageList)
				.globalResponseMessage(RequestMethod.DELETE, responseMessageList)
				.apiInfo(apiInfo()).
				select(). // 指定包
				apis(RequestHandlerSelectors.basePackage("com.imooc.controller")).
				paths(PathSelectors.any())
				.build();
	}

	private ApiInfo apiInfo(){
		return new ApiInfoBuilder()
				.title("天天吃货 电商平台接口api") // 文档标题
				// 联系人信息
				.contact(new Contact("imooc","www.imooc.com","123@imooc.com"))
				.description("专为天天吃货提供的api文档") // 详细信息
				.version("0.0.1") // 版本号
				.termsOfServiceUrl("www.imooc.com") // 网站地址
				.build();
	}
}
