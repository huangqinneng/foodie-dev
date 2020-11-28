package com.imooc;

import com.imooc.service.StuService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Auther: huangqn
 * @Date: 2020/11/22 16:53
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class Test {


	@Autowired
	private StuService stuService;

	@org.junit.Test
	public void test(){
		System.out.println(stuService.getStuInfo(1203).toString());
	}

}
