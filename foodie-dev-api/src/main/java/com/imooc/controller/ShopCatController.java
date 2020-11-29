package com.imooc.controller;

import com.imooc.bo.ShopcartBO;
import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsImg;
import com.imooc.pojo.ItemsParam;
import com.imooc.pojo.ItemsSpec;
import com.imooc.service.ItemService;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.PagedGridResult;
import com.imooc.vo.CommentLevelCountsVO;
import com.imooc.vo.ItemInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Auther: huangqn
 * @Date: 2020/11/29 17:08
 * @Description:
 */
@Api(value = "购物车接口",tags = {"购物车相关的接口"})
@RestController
@RequestMapping("shopcart")
public class ShopCatController extends BaseController{

	@Autowired
	private ItemService itemService;


	@ApiOperation(value = "加入购物车",notes = "加入购物车",httpMethod = "POST")
	@PostMapping("/add")
	public IMOOCJSONResult add(
			@ApiParam(name = "userId",value = "用户Id",required = true)
			@RequestParam String userId,
//			@ApiParam(name = "shopcartBO",value = "购物车相关字段",required = true)
			@RequestBody ShopcartBO shopcartBO,
			HttpServletRequest req, HttpServletResponse resp){
		if (StringUtils.isBlank(userId)){
			IMOOCJSONResult.errorMsg("用户存在");
		}
		// TODO 前端用户在登录的情况下，添加商品到购物车，在后端会同步到redis中

		System.out.println(shopcartBO);
		return IMOOCJSONResult.ok();
	}


	@ApiOperation(value = "从购物车中删除商品",notes = "从购物车中删除商品",httpMethod = "POST")
	@PostMapping("/del")
	public IMOOCJSONResult del(
			@ApiParam(name = "userId",value = "用户Id",required = true)
			@RequestParam String userId,
			@ApiParam(name = "itemSpecId",value = "商品规格Id",required = true)
			@RequestParam String itemSpecId,
			HttpServletRequest req, HttpServletResponse resp){
		if (StringUtils.isBlank(userId) ||
				StringUtils.isBlank(itemSpecId)){
			IMOOCJSONResult.errorMsg("用户或商品规格不能为空");
		}
		// TODO 用户在页面删除购物车数据，如果用户已登录，则需同步后端删除

		return IMOOCJSONResult.ok();
	}
}
