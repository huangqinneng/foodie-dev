package com.imooc.service.impl.center;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.bo.center.OrderItemsCommentBO;
import com.imooc.enums.YesOrNo;
import com.imooc.mapper.ItemsCommentsMapper;
import com.imooc.mapper.OrderItemsMapper;
import com.imooc.mapper.OrderStatusMapper;
import com.imooc.mapper.OrdersMapper;
import com.imooc.pojo.OrderItems;
import com.imooc.pojo.OrderStatus;
import com.imooc.pojo.Orders;
import com.imooc.service.center.MyCommentsService;
import com.imooc.utils.PagedGridResult;
import com.imooc.vo.MyCommentVO;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
public class MyCommentsServiceImpl implements MyCommentsService {

	@Autowired
	private OrderItemsMapper orderItemsMapper;

	@Autowired
	private ItemsCommentsMapper itemsCommentsMapper;
	@Autowired
	private OrdersMapper ordersMapper;
	@Autowired
	private OrderStatusMapper orderStatusMapper;

	@Autowired
	private Sid sid;


	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public List<OrderItems> queryPendingComment(String orderId){
		OrderItems orderItems = new OrderItems();
		orderItems.setOrderId(orderId);
		List<OrderItems> orderItemList = orderItemsMapper.select(orderItems);
		return orderItemList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void saveComments(String orderId, String userId,
	                         List<OrderItemsCommentBO> commentList) {
		// 1. 保存评价内容 items_comments
		for (OrderItemsCommentBO sic:commentList){
			sic.setCommentId(sid.nextShort());
		}
		Map<String,Object> paramsMap = new HashMap<>();
		paramsMap.put("userId",userId);
		paramsMap.put("commentList",commentList);
		itemsCommentsMapper.saveComments(paramsMap);
		// 2. 修改订单表为已评价 orders
		Orders orders = new Orders();
		orders.setId(orderId);
		orders.setIsComment(YesOrNo.YES.type);
		orders.setUpdatedTime(new Date());
		ordersMapper.updateByPrimaryKeySelective(orders);
		// 3. 修改订单状态表的留言时间 order_status
		OrderStatus orderStatus = new OrderStatus();
		orderStatus.setOrderId(orderId);
		orderStatus.setCommentTime(new Date());
		orderStatusMapper.updateByPrimaryKeySelective(orderStatus);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public PagedGridResult queryMyComments(String userId, Integer page, Integer pageSize) {
		PageHelper.startPage(page,pageSize);
		Map<String,Object> paramsMap = new HashMap<>();
		paramsMap.put("page",page);
		paramsMap.put("pageSize",pageSize);
		paramsMap.put("userId",userId);
		List<MyCommentVO> myCommentVOS = itemsCommentsMapper.queryMyComments(paramsMap);
		PagedGridResult pagedGridResult = this.setterPagedGrid(myCommentVOS, page);
		return pagedGridResult;
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
