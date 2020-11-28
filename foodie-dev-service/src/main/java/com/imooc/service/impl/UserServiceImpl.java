package com.imooc.service.impl;

import com.imooc.bo.UserBO;
import com.imooc.enums.Sex;
import com.imooc.mapper.UsersMapper;
import com.imooc.pojo.Users;
import com.imooc.service.UserService;
import com.imooc.utils.DateUtil;
import com.imooc.utils.MD5Utils;
import org.n3r.idworker.Sid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

/**
 * @Auther: huangqn
 * @Date: 2020/11/22 16:17
 * @Description: 用户服务相关
 */
@Service
public class UserServiceImpl implements UserService {

	Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	// 默认头像图标
	public static final String USER_FACE = "http://122.152.205.72:88/group1/M00/00/05/CpoxxFw_8_qAIlFXAAAcIhVPdSg994.png";
	// 生成唯一ID工具类
	@Autowired
	private Sid sid;

	@Autowired
	private UsersMapper usersMapper;

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public boolean queryUserNameIsExist(String userName) {
		Example userExample = new Example(Users.class);
		Example.Criteria criteria = userExample.createCriteria();
		criteria.andEqualTo("username", userName);
		int i = usersMapper.selectCountByExample(userExample);
		log.info("根据用户名查询到：{}条",i);
		return i == 0 ? false : true;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public Users createUser(UserBO userBO) throws Exception {
		Users users = new Users();
		try {
			users.setId(sid.nextShort());
			if (!userBO.getPassword().equals(userBO.getConfirmPassword())) {

			}
			users.setUsername(userBO.getUsername());
			users.setPassword(MD5Utils.getMD5Str(userBO.getPassword()));
			// 默认用户昵称
			users.setNickname(userBO.getUsername());
			// 默认头像图片
			users.setFace(USER_FACE);
			// 默认生日
			users.setBirthday(DateUtil.stringToDate("1910-01-01"));
			// 默认性别-保密
			users.setSex(Sex.SECRET.type);
			Date date = DateUtil.getCurrentDateTime();
			users.setCreatedTime(date);
			users.setUpdatedTime(date);

			usersMapper.insert(users);
		} catch (Exception e) {
			log.error("注册用户失败：{}",e);
			throw new Exception("");
		}
		return users;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Users queryUserForLogin(String username,String password) throws Exception {
		long time = DateUtil.getCurrentDateTime().getTime();
		Users users = null;
		try {
			log.info("登录代理商用户名：{}",username);
			Example userExample = new Example(Users.class);
			Example.Criteria criteria = userExample.createCriteria();
			criteria.andEqualTo("username", username);
			criteria.andEqualTo("password", MD5Utils.getMD5Str(password));
			users = usersMapper.selectOneByExample(userExample);
		} catch (Exception e) {
			log.error("查询登录用户信息失败：{}",e);
			throw new Exception("根据用户名密码查询用户失败");
		}
		log.info("执行时间：{}" ,DateUtil.getCurrentDateTime().getTime() - time);
		return users;
	}


}
