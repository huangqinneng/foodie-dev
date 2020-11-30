package com.imooc.service;

import com.imooc.bo.ShopcartBO;
import com.imooc.bo.SubmitOrderBO;
import com.imooc.pojo.OrderStatus;
import com.imooc.vo.OrderVO;

import java.util.List;

public interface OrderService {

    /**
     * 用于创建订单
     * @param shopcartList
     * @param submitOrderBO
     */
    OrderVO createOrder(List<ShopcartBO> shopcartList, SubmitOrderBO submitOrderBO);

    /**
     * 修改订单状态
     * @param orderId
     * @param orderStatus
     */
    void updateOrderStatus(String orderId, Integer orderStatus);

    /**
     * 查询订单状态
     */
    OrderStatus queryOrderStatusInfo(String orderId);

	/**
	 * 关闭超时未支付的订单
	 */
	void closeOrder();
}
