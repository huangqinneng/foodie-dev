package com.imooc.service.impl;

import com.imooc.mapper.StuMapper;
import com.imooc.pojo.Stu;
import com.imooc.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Auther: huangqn
 * @Date: 2020/11/22 16:17
 * @Description:
 */
@Service
public class StuServiceImpl implements StuService {

	@Autowired
	private StuMapper stuMapper;

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Stu getStuInfo(int id) {
		return stuMapper.selectByPrimaryKey(id);
	}
}