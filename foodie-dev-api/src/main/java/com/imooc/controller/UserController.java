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
public class UserController {


	@Autowired
	private UserService userService;


	@GetMapping("/hello")
	public String getHollerWord(String userName){
		boolean b = userService.queryUserNameIsExist(userName);

		return "Hello World!";
	}
}
