package com.imooc.service.impl.center;

import com.imooc.bo.center.CenterUsersBO;
import com.imooc.mapper.UsersMapper;
import com.imooc.pojo.Users;
import com.imooc.service.center.CenterUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Auther: huangqn
 * @Date: 2020/12/1 09:39
 * @Description: 用户中心 相关服务类
 */
@Service
public class CenterUserServiceImpl implements CenterUserService {

	@Autowired
	private UsersMapper usersMapper;

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Users queryUserInfo(String userId) {
		Users users = usersMapper.selectByPrimaryKey(userId);
		users.setPassword(null);
		return users;
	}
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public Users updateUserInfo(String userId, CenterUsersBO centerUsersBO) {
		Users users = new Users();
		/*users.setId(userId);
		users.setNickname(centerUsersBO.getNickname());
		users.setSex(centerUsersBO.getSex());
		users.setBirthday(centerUsersBO.getBirthday());
		users.setMobile(centerUsersBO.getMobile());
		users.setEmail(centerUsersBO.getEmail());*/
		BeanUtils.copyProperties(centerUsersBO,users);
		users.setId(userId);
		users.setUpdatedTime(new Date());
		int i = usersMapper.updateByPrimaryKeySelective(users);
		Users users1 = queryUserInfo(userId);
		return users1;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public Users updateUserFace(String userId, String faceUrl) {
		Users users = new Users();
		users.setId(userId);
		users.setFace(faceUrl);
		users.setUpdatedTime(new Date());
		int i = usersMapper.updateByPrimaryKeySelective(users);
		Users users1 = queryUserInfo(userId);
		return users1;
	}
}
