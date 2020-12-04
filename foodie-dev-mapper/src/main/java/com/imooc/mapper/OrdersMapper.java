package com.imooc.mapper;

import com.imooc.my.mapper.MyMapper;
import com.imooc.pojo.OrderStatus;
import com.imooc.pojo.Orders;
import com.imooc.vo.MyOrdersVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrdersMapper extends MyMapper<Orders> {

	/**
	 * 查询的我的订单
	 * @param paramsMap
	 * @return
	 */
	List<MyOrdersVO> queryMyOrders(@Param("paramsMap") Map<String,Object> paramsMap);

	/**
	 * 查询订单状态
	 * @param paramsMap
	 * @return
	 */
	int getMyOrderStatusCounts(@Param("paramsMap") Map<String,Object> paramsMap);

	/**
	 * 查询订单动向
	 * @param paramsMap
	 * @return
	 */
	List<OrderStatus> getMyOrderTrend(@Param("paramsMap") Map<String,Object> paramsMap);
}