package com.imooc.controller;

import com.imooc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @Auther: huangqn
 * @Date: 2020/11/22 12:11
 * @Description:
 */
@RestController
@ApiIgnore
public class BaseController {

	/**
	 * 商品评价默认显示条数
	 */
	public static final Integer COMMENT_PAGE_SIZE = 10;
	/**
	 * 搜索商品默认显示条数
	 */
	public static final Integer PAGE_SIZE = 20;

}
