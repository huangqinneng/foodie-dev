package com.imooc.service.impl.center;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.YesOrNo;
import com.imooc.mapper.OrderStatusMapper;
import com.imooc.mapper.OrdersMapper;
import com.imooc.pojo.OrderStatus;
import com.imooc.pojo.Orders;
import com.imooc.service.center.MyOrdersService;
import com.imooc.utils.PagedGridResult;
import com.imooc.vo.MyOrdersVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: huangqn
 * @Date: 2020/12/2 10:57
 * @Description:
 */
@Service
public class MyOrdersServiceImpl implements MyOrdersService {

	@Autowired
	private OrdersMapper ordersMapper;
	@Autowired
	private OrderStatusMapper orderStatusMapper;

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public PagedGridResult queryMyOrders(String userId, String orderStatus, Integer page, Integer pageSize) {
		PageHelper.startPage(page, pageSize);

		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("userId", userId);
		paramsMap.put("orderStatus", orderStatus);
		paramsMap.put("page", page);
		paramsMap.put("pageSize", pageSize);
		List<MyOrdersVO> myOrdersVOS =
				ordersMapper.queryMyOrders(paramsMap);
		PagedGridResult pagedGridResult = this.setterPagedGrid(myOrdersVOS, page);
		return pagedGridResult;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int updateDeliverOrderStatus(String orderId) {
		OrderStatus orderStatus = new OrderStatus();
		orderStatus.setDeliverTime(new Date());
		orderStatus.setOrderStatus(OrderStatusEnum.WAIT_RECEIVE.type);
		Example example = new Example(OrderStatus.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("orderId", orderId);
		criteria.andEqualTo("orderStatus", OrderStatusEnum.WAIT_RECEIVE.type);
		int i = orderStatusMapper.updateByExampleSelective(orderStatus, example);
		return i;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public Orders queryMyOrder(String userId, String orderId) {
		Orders orders = new Orders();
		orders.setId(orderId);
		orders.setUserId(userId);
		orders.setIsDelete(YesOrNo.NO.type);
		orders = ordersMapper.selectOne(orders);
		return orders;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public boolean updateReceiveOrderStatus(String orderId) {
		OrderStatus orderStatus = new OrderStatus();
		orderStatus.setOrderId(orderId);
		orderStatus.setOrderStatus(OrderStatusEnum.SUCCESS.type);
		orderStatus.setSuccessTime(new Date());

		Example example = new Example(OrderStatus.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("orderId", orderId);
		criteria.andEqualTo("orderStatus", OrderStatusEnum.SUCCESS.type);
		int i = orderStatusMapper.updateByExampleSelective(orderStatus, example);
		return i == 1 ? true : false;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public boolean deleteOrder(String userId, String orderId) {
		Orders orders = new Orders();
//		orders.setUserId(userId);
//		orders.setId(orderId);
		orders.setIsDelete(YesOrNo.YES.type);
		orders.setUpdatedTime(new Date());
		Example example = new Example(Orders.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("id", orderId);
		criteria.andEqualTo("userId", userId);
		int i = ordersMapper.updateByExampleSelective(orders, example);
		return i == 1 ? true : false;
	}

	private PagedGridResult setterPagedGrid(List<?> list, Integer page) {
		PageInfo<?> pageList = new PageInfo<>(list);
		PagedGridResult grid = new PagedGridResult();
		grid.setPage(page);
		grid.setRows(list);
		grid.setTotal(pageList.getPages());
		grid.setRecords(pageList.getTotal());
		return grid;
	}
}
