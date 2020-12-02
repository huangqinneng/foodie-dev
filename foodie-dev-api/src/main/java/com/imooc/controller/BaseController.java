package com.imooc.controller;

import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.io.File;

/**
 * @Auther: huangqn
 * @Date: 2020/11/22 12:11
 * @Description:
 */
@RestController
@ApiIgnore
public class BaseController {

	public static final String FOODIE_SHOPCART = "shopcart";
	/**
	 * 商品评价默认显示条数
	 */
	public static final Integer COMMENT_PAGE_SIZE = 10;
	/**
	 * 搜索商品默认显示条数
	 */
	public static final Integer PAGE_SIZE = 20;

	// 微信支付中心->支付中心->天天吃货平台
	//                    |->回调通知的url
	// 这样写本地地址回调的时候肯定访问不到，需要用natapp做个内网穿透
	String payReturnUrl = "http://localhost:8088/orders/notifyMerchantOrderPaid";

	// 支付中心的调用地址
	String paymentUrl = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";
	//	D:\project\foodie\foodie-dev\foodie-dev-api\src\main\resources\images
	public static final String IMAGE_USER_FACE_LOCATION = "D:" + File.separator +
			"project" + File.separator +
			"foodie" + File.separator +
			"foodie-dev"  + File.separator +
			"foodie-dev-api" + File.separator +
			"src" + File.separator +
			"main" + File.separator +
			"resources"  + File.separator +
			"images"  + File.separator;


//	public static final String IMAGE_USER_FACE_LOCATION = "classpath:/images/";


}
