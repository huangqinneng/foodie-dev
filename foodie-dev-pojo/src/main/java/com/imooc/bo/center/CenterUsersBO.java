package com.imooc.bo.center;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @Auther: huangqn
 * @Date: 2020/12/1 10:51
 * @Description: 用户中心，请求用户数据BO
 */
@ApiModel(value = "用户中心-用户请求对象BO",description = "从客户端，由用户传入的数据封装在此entity中")
public class CenterUsersBO {

	@NotBlank(message = "用户名不能为空")
	@ApiModelProperty(value = "用户名",name = "username",example = "imooc",required = true)
	private String username; // 用户名

	@ApiModelProperty(value = "用户昵称",name = "nickname",example = "小星星",required = true)
	private String nickname; // 用户昵称

	@Length(max = 8,message = "真实姓名不能超过8位")
	@ApiModelProperty(value = "真实姓名",name = "realname",example = "小小",required = false)
	private String realname; // 真实姓名

	@NotBlank(message = "手机号不能为空")
	@Length(max = 11,message = "手机号不能超过11位")
	@ApiModelProperty(value = "手机号",name = "mobile",example = "13111111111",required = false)
	private String mobile; // 手机号

	@Length(max = 20,message = "邮箱不能超过20位")
	@ApiModelProperty(value = "邮箱地址",name = "email",example = "imooc@imooc.com",required = false)
	private String email; // 邮箱地址

	@ApiModelProperty(value = "性别",name = "sex",example = "0:女，1:男，2:保密",required = true)
	private Integer sex; // 性别

	@ApiModelProperty(value = "生日",name = "birthday",example = "1900:01:01",required = false)
	private Date birthday; // 生日


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
}
