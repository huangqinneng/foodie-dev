package com.imooc.service;

import com.imooc.bo.UserBO;
import com.imooc.pojo.Users;

/**
 * @Auther: huangqn
 * @Date: 2020/11/22 16:17
 * @Description: 用户相关服务类
 */
public interface UserService {

	/**
	 * 查询用户名是否存在
	 * @param userName
	 * @return
	 */
	boolean queryUserNameIsExist(String userName);

	/**
	 * 创建用户
	 * @param userBO
	 * @return
	 */
	Users createUser(UserBO userBO) throws Exception;

	/**
	 * 登录
	 * @return
	 * @throws Exception
	 */
	Users queryUserForLogin(String username,String password) throws Exception;
}
