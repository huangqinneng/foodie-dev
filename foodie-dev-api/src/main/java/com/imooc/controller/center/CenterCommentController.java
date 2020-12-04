package com.imooc.controller.center;

import com.imooc.bo.center.OrderItemsCommentBO;
import com.imooc.controller.BaseController;
import com.imooc.enums.YesOrNo;
import com.imooc.pojo.OrderItems;
import com.imooc.pojo.Orders;
import com.imooc.service.center.MyCommentsService;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.PagedGridResult;
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
 * @Date: 2020/12/1 09:35
 * @Description: 用户中心
 */
@Api(value = "center - 我的评价", tags = {"我的评价展示相关接口"})
@RestController
@RequestMapping("mycomments")
public class CenterCommentController extends BaseController {

	@Autowired
	private MyCommentsService myCommentsService;

	@PostMapping("/pending")
	@ApiOperation(value = "评价商品", notes = "评价商品", httpMethod = "POST")
	public IMOOCJSONResult pending(@RequestParam
	                                @ApiParam(name = "userId",value = "用户Id",required = true)
			                        String userId,
	                                @RequestParam
	                                @ApiParam(name = "orderId",value = "订单Id",required = true)
			                        String orderId) {
		// 判断用户和订单是否关联
		IMOOCJSONResult imoocjsonResult = this.checkOrder(userId, orderId);
		if (!imoocjsonResult.isOK()){
			return imoocjsonResult;
		}
		// 判断订单是否评价过
		Orders orders = (Orders)imoocjsonResult.getData();
		if (orders !=null && orders.getIsComment() != YesOrNo.NO.type){
			return IMOOCJSONResult.errorMsg("该笔订单已经评价过");
		}

		List<OrderItems> orderItems = myCommentsService.queryPendingComment(orderId);
		return IMOOCJSONResult.ok(orderItems);
	}


	@PostMapping("/saveList")
	@ApiOperation(value = "评价商品", notes = "评价商品", httpMethod = "POST")
	public IMOOCJSONResult saveList(@RequestParam
	                                @ApiParam(name = "userId",value = "用户Id",required = true)
			                                String userId,
	                                @RequestParam
	                                @ApiParam(name = "orderId",value = "订单Id",required = true)
			                                String orderId,
	                                @RequestBody
	                                        List<OrderItemsCommentBO> commentList) {
		// 判断用户和订单是否关联
		IMOOCJSONResult imoocjsonResult = this.checkOrder(userId, orderId);
		if (!imoocjsonResult.isOK()){
			return imoocjsonResult;
		}
		System.out.println(commentList);
		if (commentList == null || commentList.size() == 0){
			return IMOOCJSONResult.errorMsg("评论内容不能为空");
		}
		// 判断订单是否评价过
		Orders orders = (Orders)imoocjsonResult.getData();
		if (orders !=null && orders.getIsComment() != YesOrNo.NO.type){
			return IMOOCJSONResult.errorMsg("该笔订单已经评价过");
		}
		myCommentsService.saveComments(orderId,userId,commentList);
		return IMOOCJSONResult.ok();
	}


	@PostMapping("/query")
	@ApiOperation(value = "查询我的评价列表", notes = "查询我的评价列表", httpMethod = "POST")
	public IMOOCJSONResult query(@ApiParam(name = "userId", value = "用户Id", required = true)
	                             @RequestParam String userId,
	                             @ApiParam(name = "page", value = "下一页，页数", required = false)
	                             @RequestParam Integer page,
	                             @ApiParam(name = "pageSize", value = "每页显示几条", required = false)
	                             @RequestParam  Integer pageSize,
	                             HttpServletRequest request, HttpServletResponse response
	) {
		if (StringUtils.isBlank(userId)) {
			return IMOOCJSONResult.errorMsg("");
		}
		if (page == null){
			page = 1;
		}
		if (pageSize == null){
			pageSize = PAGE_SIZE;
		}

		PagedGridResult pagedGridResult = myCommentsService.queryMyComments(userId, page, pageSize);
		return IMOOCJSONResult.ok(pagedGridResult);
	}


}
