package com.imooc.service.center;

import com.imooc.bo.center.CenterUsersBO;
import com.imooc.pojo.Users;

/**
 * @Auther: huangqn
 * @Date: 2020/11/22 16:17
 * @Description: 用户中心 相关服务类
 */
public interface CenterUserService {

	/**
	 * 根据用户Id查询用户信息
	 * @return
	 */
	Users queryUserInfo(String userId);

	/**
	 * 修改用户信息
	 * @param userId
	 * @param centerUsersBO
	 */
	Users updateUserInfo(String userId, CenterUsersBO centerUsersBO);

	/**
	 *
	 * @param userId
	 * @param faceUrl
	 * @return
	 */
	Users updateUserFace(String userId, String faceUrl);
}
