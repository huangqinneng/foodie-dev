package com.imooc.controller.center;

import com.imooc.pojo.Users;
import com.imooc.service.center.CenterUserService;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: huangqn
 * @Date: 2020/12/1 09:35
 * @Description: 用户中心
 */
@Api(value = "center - 用户中心", tags = {"用户中心展示相关接口"})
@RestController
@RequestMapping("center")
public class CenterController {

	@Autowired
	private CenterUserService centerUserService;

	@GetMapping("/userInfo")
	@ApiOperation(value = "获取用户信息", notes = "获取用户信息", httpMethod = "GET")
	public IMOOCJSONResult userInfo(@RequestParam
	                                @ApiParam(name = "userId",value = "用户Id",required = true)
			                        String userId) {
		Users users = centerUserService.queryUserInfo(userId);

		return IMOOCJSONResult.ok(users);
	}



}
