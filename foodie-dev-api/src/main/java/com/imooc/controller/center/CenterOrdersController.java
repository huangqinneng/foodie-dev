package com.imooc.controller.center;

import com.imooc.controller.BaseController;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.PagedGridResult;
import com.imooc.vo.OrderStatusCountsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: huangqn
 * @Date: 2020/12/1 09:35
 * @Description: 用户订单管理
 */
@Api(value = "用户订单管理", tags = {"用户订单管理相关接口"})
@RestController
@RequestMapping("myorders")
public class CenterOrdersController extends BaseController {



	@PostMapping("/query")
	@ApiOperation(value = "查询订单列表", notes = "查询订单列表", httpMethod = "POST")
	public IMOOCJSONResult query(@ApiParam(name = "userId", value = "用户Id", required = true)
	                              @RequestParam String userId,
	                              @ApiParam(name = "orderStatus", value = "订单状态", required = false)
	                              @RequestParam String orderStatus,
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
//			pageSize = PAGE_SIZE;
			pageSize = 3;
		}
		PagedGridResult pagedGridResult = myOrdersService.queryMyOrders(userId, orderStatus, page, pageSize);

		return IMOOCJSONResult.ok(pagedGridResult);
	}


	@GetMapping("/deliver")
	@ApiOperation(value = "商家发货", notes = "商家发货", httpMethod = "GET")
	public IMOOCJSONResult deliver(@ApiParam(name = "orderId", value = "订单Id", required = true)
	                              @RequestParam String orderId
	) {
		if (StringUtils.isBlank(orderId)) {
			return IMOOCJSONResult.errorMsg("订单Id不能为空");
		}
		int i = myOrdersService.updateDeliverOrderStatus(orderId);

		return IMOOCJSONResult.ok();
	}

	@PostMapping("/confirmReceive")
	@ApiOperation(value = "确认收货", notes = "确认收货", httpMethod = "POST")
	public IMOOCJSONResult confirmReceive(@ApiParam(name = "userId", value = "用户Id", required = true)
	                               @RequestParam String userId,
                                  @ApiParam(name = "orderId", value = "订单Id", required = true)
                                  @RequestParam String orderId
	) {
		if (StringUtils.isBlank(orderId)) {
			return IMOOCJSONResult.errorMsg("订单Id不能为空");
		}
		IMOOCJSONResult imoocjsonResult = this.checkOrder(userId, orderId);
		if (!imoocjsonResult.isOK()){
			return imoocjsonResult;
		}

		boolean b = myOrdersService.updateReceiveOrderStatus(orderId);
		if(!b){
			return IMOOCJSONResult.errorMsg("订单确认收货失败");
		}

		return IMOOCJSONResult.ok();
	}

	@PostMapping("/delete")
	@ApiOperation(value = "删除订单", notes = "删除订单", httpMethod = "POST")
	public IMOOCJSONResult delete(@ApiParam(name = "userId", value = "用户Id", required = true)
	                                      @RequestParam String userId,
	                                      @ApiParam(name = "orderId", value = "订单Id", required = true)
	                                      @RequestParam String orderId
	) {
		if (StringUtils.isBlank(orderId)) {
			return IMOOCJSONResult.errorMsg("订单Id不能为空");
		}
		IMOOCJSONResult imoocjsonResult = this.checkOrder(userId, orderId);
		if (!imoocjsonResult.isOK()){
			return imoocjsonResult;
		}
		boolean b = myOrdersService.deleteOrder(userId,orderId);
		if(!b){
			return IMOOCJSONResult.errorMsg("订单删除失败");
		}

		return IMOOCJSONResult.ok();
	}


	@PostMapping("/statusCounts")
	@ApiOperation(value = "获得订单数概况", notes = "获得订单数概况", httpMethod = "POST")
	public IMOOCJSONResult statusCounts(@ApiParam(name = "userId", value = "用户Id", required = true)
	                              @RequestParam String userId
	) {
		if (StringUtils.isBlank(userId)) {
			return IMOOCJSONResult.errorMsg("用户Id不能为空");
		}
		OrderStatusCountsVO orderStatusCounts = myOrdersService.getOrderStatusCounts(userId);

		return IMOOCJSONResult.ok(orderStatusCounts);
	}

	@PostMapping("/trend")
	@ApiOperation(value = "获得订单数概况", notes = "获得订单数概况", httpMethod = "POST")
	public IMOOCJSONResult trend(@ApiParam(name = "userId", value = "用户Id", required = true)
	                                    @RequestParam String userId,
			                             @ApiParam(name = "page", value = "下一页，页数", required = false)
			                             @RequestParam Integer page,
			                             @ApiParam(name = "pageSize", value = "每页显示几条", required = false)
	                                     @RequestParam  Integer pageSize
	) {
		if (StringUtils.isBlank(userId)) {
			return IMOOCJSONResult.errorMsg("用户Id不能为空");
		}
		PagedGridResult ordersTrend = myOrdersService.getOrdersTrend(userId, page, pageSize);

		return IMOOCJSONResult.ok(ordersTrend);
	}




}
