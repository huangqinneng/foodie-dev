package com.imooc.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Auther: huangqn
 * @Date: 2020/11/23 11:13
 * @Description: 接收前端  用户-请求参数
 */
@ApiModel(value = "用户请求对象BO",description = "从客户端，由用户传入的数据封装在此entity中")
public class UserBO {

	@ApiModelProperty(value = "用户名",name = "username",example = "imooc",required = true)
	private String username; // 用户名
	@ApiModelProperty(value = "密码",name = "password",example = "123456",required = true)
	private String password; // 密码
	@ApiModelProperty(value = "确认密码",name = "confirmPassword",example = "123456",required = false)
	private String confirmPassword; // 确认密码

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
