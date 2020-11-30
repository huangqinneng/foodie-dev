package com.imooc.controller;

import com.imooc.bo.ShopcartBO;
import com.imooc.bo.SubmitOrderBO;
import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.PayMethod;
import com.imooc.pojo.OrderStatus;
import com.imooc.service.OrderService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.JsonUtils;
import com.imooc.vo.MerchantOrdersVO;
import com.imooc.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

//@Controller springmvc用的比较多，做页面跳转
@Api(value = "订单相关", tags = {"订单相关的api接口"})
@RestController
@RequestMapping("orders")
public class OrdersController extends BaseController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private RestTemplate restTemplate;

//    @Autowired
//    private RedisOperator redisOperator;

	@ApiOperation(value = "用户下单", notes = "用户下单", httpMethod = "POST")
	@PostMapping("/create")
	public IMOOCJSONResult create(@RequestBody SubmitOrderBO submitOrderBO,
	                              HttpServletRequest request,
	                              HttpServletResponse response) {

		if (!submitOrderBO.getPayMethod().equals(PayMethod.WEIXIN.type)
				&& !submitOrderBO.getPayMethod().equals(PayMethod.ALIPAY.type)) {
			return IMOOCJSONResult.errorMsg("支付方式不支持");
		}
		// 1. 创建订单
		// 2. 创建订单以后，移除购物车中已结算的商品
		// 3. 向支付中心发送当前订单，用于保存支付中心的订单数据
		System.out.println(submitOrderBO);

		/*String shopcartJson = redisOperator.get(FOODIE_SHOPCART + ":" + submitOrderBO.getUserId());
		if (StringUtils.isBlank(shopcartJson)) {
			return IMOOCJSONResult.errorMsg("购物数据错误");
		}*/

//		List<ShopcartBO> shopcartList = JsonUtils.jsonToList(shopcartJson, ShopcartBO.class);
		List<ShopcartBO> shopcartList = new ArrayList<>();


		// 1. 创建订单
		OrderVO orderVO = orderService.createOrder(shopcartList, submitOrderBO);
//		String orderId = merchantOrdersVO.getMerchantOrderId();
		String orderId = orderVO.getOrderId();
		MerchantOrdersVO merchantOrdersVO = orderVO.getMerchantOrdersVO();

		// 2. 移除了购物车中已结算的商品,覆盖购物车
		String shopcartListToJson = JsonUtils.objectToJson(shopcartList);
//        redisOperator.set(FOODIE_SHOPCART + ":" + submitOrderBO.getUserId(),shopcartListToJson);

		//目前是全部清空，部分清空后续会完善
		CookieUtils.setCookie(request, response, FOODIE_SHOPCART, shopcartListToJson, true);

		// 3. 向支付中心发送当前订单，用于保存支付中心的相关数据
		//直接调用另一个接口，不用再返给前端
		merchantOrdersVO.setReturnUrl(payReturnUrl);
//		// 为了方便测试，所有的支付金额统一改为1分钱
//		merchantOrdersVO.setAmount(1);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("imoocUserId", "imooc");
		headers.add("password", "imooc");

		HttpEntity<MerchantOrdersVO> entity = new HttpEntity<>(merchantOrdersVO, headers);

		ResponseEntity<IMOOCJSONResult> responseEntity = restTemplate.postForEntity(paymentUrl, entity, IMOOCJSONResult.class);
		IMOOCJSONResult paymentResult = responseEntity.getBody();
		if (paymentResult.getStatus() != 200) {
			return IMOOCJSONResult.errorMsg("支付中心订单创建失败,请联系管理员");
		}


		return IMOOCJSONResult.ok(orderId);
	}


	/**
	 * 订单支付回调
	 * @param merchantOrderId
	 * @return
	 */
	@PostMapping("notifyMerchantOrderPaid")
	public Integer notifyMerchantOrderPaid(String merchantOrderId) {
		orderService.updateOrderStatus(merchantOrderId, OrderStatusEnum.WAIT_DELIVER.type);
		return HttpStatus.OK.value();
	}

	/**
	 * 获取已经支付的订单信息
	 * @param orderId
	 * @return
	 */
	@PostMapping("getPaidOrderInfo")
	public IMOOCJSONResult getPaidOrderInfo(String orderId) {
		OrderStatus orderStatus = orderService.queryOrderStatusInfo(orderId);
		return IMOOCJSONResult.ok(orderStatus);
	}

}
