package com.imooc.service.center;

import com.imooc.bo.center.OrderItemsCommentBO;
import com.imooc.pojo.OrderItems;
import com.imooc.utils.PagedGridResult;

import java.util.List;

/**
 * @Auther: huangqn
 * @Date: 2020/12/3 09:33
 * @Description: 我的评论
 */
public interface MyCommentsService {

	/**
	 * 根据订单Id查询相关评论
	 * @param orderId
	 * @return
	 */
	List<OrderItems> queryPendingComment(String orderId);

	/**
	 * 保存评论信息
	 * @param orderId
	 * @param userId
	 * @param commentList
	 */
	void saveComments(String orderId,String userId,List<OrderItemsCommentBO> commentList);

	/**
	 * 查询我的评价
	 * @param userId
	 * @param page
	 * @param pageSize
	 * @return
	 */
	PagedGridResult queryMyComments(String userId,
	                                Integer page,
	                                Integer pageSize);
}
