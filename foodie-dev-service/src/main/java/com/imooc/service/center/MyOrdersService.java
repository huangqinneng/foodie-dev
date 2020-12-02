package com.imooc.service.center;

import com.imooc.pojo.Orders;
import com.imooc.utils.PagedGridResult;

/**
 * @Auther: huangqn
 * @Date: 2020/12/2 10:54
 * @Description: 个人中心 订单服务
 */
public interface MyOrdersService {

	/**
	 * 查看我的订单列表
	 * @param userId
	 * @param orderStatus
	 * @param page
	 * @param pageSize
	 * @return
	 */
	PagedGridResult queryMyOrders(String userId,
	                              String orderStatus,
	                              Integer page,
	                              Integer pageSize);

	/**
	 * 商家发货
	 * @param orderId
	 * @return
	 */
	int updateDeliverOrderStatus(String orderId);

	/**
	 * 根据用户ID和订单Id查询订单
	 * @param userId
	 * @param orderId
	 * @return
	 */
	Orders queryMyOrder(String userId,String orderId);

	/**
	 * 更新订单状态为  确认收货
	 * @param orderId
	 * @return
	 */
	boolean updateReceiveOrderStatus(String orderId);

	/**
	 * 删除订单
	 * @param userId
	 * @param orderId
	 * @return
	 */
	boolean deleteOrder(String userId,String orderId);

}
