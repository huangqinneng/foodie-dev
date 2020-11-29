package com.imooc.controller;

import com.imooc.bo.UserBO;
import com.imooc.pojo.Users;
import com.imooc.service.UserService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.JsonUtils;
import io.swagger.annotations.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: huangqn
 * @Date: 2020/11/22 12:11
 * @Description:
 */
@Api(value = "注册登录相关",tags = {"用于注册登录相关的接口"})
@RestController
@RequestMapping("passport")
public class PassPortController {


	@Autowired
	private UserService userService;


	@ApiOperation(value = "用户名是否存在",notes = "用户名是否存在",httpMethod = "GET")
	@ApiImplicitParam(name = "username", value = "用户名",  required = true, dataType = "String") // paramType = "query",
	@GetMapping("/usernameIsExist")
	public IMOOCJSONResult userNameIsExist(@RequestParam String username){
		// 判断用户名不能为空
		if (StringUtils.isBlank(username)){
			return IMOOCJSONResult.errorMsg("用户名不能为空");
		}
		// 查询用户名是否存在
		boolean b = userService.queryUserNameIsExist(username);
		// 如果已存在账号，则返回提示
		if (b){
			return IMOOCJSONResult.errorMsg("用户名已存在");
		}
		// 请求成功
		return IMOOCJSONResult.ok();
	}

	@ApiOperation(value = "用户注册",notes = "用户注册",httpMethod = "POST",response = IMOOCJSONResult.class)
	@ApiResponses({@ApiResponse(code = 500, message = "服务器内部错误", response = IMOOCJSONResult.class)})
	@PostMapping("/regist")
	public IMOOCJSONResult regist(@RequestBody UserBO userBO,
	                              HttpServletRequest request, HttpServletResponse response){
		String userName = userBO.getUsername();
		String passWord = userBO.getPassword();
		String confirmPassWord = userBO.getConfirmPassword();
		// 1.判断用户名密码不能为空
		if (StringUtils.isBlank(userName) || StringUtils.isBlank(passWord) ||
				StringUtils.isBlank(confirmPassWord) ){
			return IMOOCJSONResult.errorMsg("用户名或密码不能为空");
		}
		// 2.判断用户名是否存在
		boolean b = userService.queryUserNameIsExist(userName);
		// 如果已存在账号，则返回提示
		if (b){
			return IMOOCJSONResult.errorMsg("用户名已存在");
		}
		// 3.判断密码长度不能小于6位
		if (passWord.length() < 6 || confirmPassWord.length() < 6){
			return IMOOCJSONResult.errorMsg("密码长度不能小于6位");
		}
		// 4.判断两次密码是否相等
		if (!passWord.equals(confirmPassWord)){
			return IMOOCJSONResult.errorMsg("密码和确认密码不一致");
		}
		// 5.保存
		try {
			Users user = userService.createUser(userBO);
			user = setNullProperty(user);
			CookieUtils.setCookie(request,response,"user",
					JsonUtils.objectToJson(user),true);
			// TODO 生产用户token ,存入redis
			// TODO 同步购物车数据
			return IMOOCJSONResult.ok(user);
		} catch (Exception e) {
			// 请求失败
			return IMOOCJSONResult.errorMsg("注册用户失败");
		}

	}

	@ApiOperation(value = "用户登录",notes = "用户登录",httpMethod = "POST")
	@ApiResponses(value = {})
	@PostMapping("/login")
	public IMOOCJSONResult login(@RequestBody UserBO userBO,
	                             HttpServletRequest request, HttpServletResponse response){
		// 判断用户名不能为空
		if (StringUtils.isBlank(userBO.getUsername()) || StringUtils.isBlank(userBO.getPassword())){
			return IMOOCJSONResult.errorMsg("用户名或密码不能为空");
		}
		Users users = null;
		try {
			// 查询用户名是否存在
			users = userService.queryUserForLogin(userBO.getUsername(), userBO.getPassword());
			if (!ObjectUtils.anyNotNull(users)){
				return IMOOCJSONResult.errorMsg("用户名或密码错误");
			}
			users = setNullProperty(users);
			CookieUtils.setCookie(request,response,"user",
					JsonUtils.objectToJson(users),true);
		} catch (Exception e) {
			return IMOOCJSONResult.errorMsg("用户登录-查询用户信息出错");
		}

		// TODO 生产用户token ,存入redis
		// TODO 同步购物车数据

		// 请求成功
		return IMOOCJSONResult.ok(users);
	}

	/**
	 * 将敏感信息置空
	 * @param users
	 * @return
	 */
	private Users setNullProperty(Users users){
		users.setPassword(null);
		users.setRealname(null);
		users.setEmail(null);
		users.setMobile(null);
		users.setCreatedTime(null);
		users.setUpdatedTime(null);
		users.setBirthday(null);
		return users;
	}
	@ApiOperation(value = "用户退出登录",notes = "用户退出登录",httpMethod = "POST")
	@ApiResponses(value = {})
	@PostMapping("/logout")
	public IMOOCJSONResult logout(@RequestParam String userId,
	                             HttpServletRequest request, HttpServletResponse response){
		// 用户ID不能为空
		if (StringUtils.isBlank(userId)){
			return IMOOCJSONResult.errorMsg("用户ID不能为空");
		}
		CookieUtils.deleteCookie(request,response,"user");

		// TODO 用户退出登录，需要清空购物车
		// TODO 分布式会话中，需要清除用户数据

		// 请求成功
		return IMOOCJSONResult.ok();
	}
}
