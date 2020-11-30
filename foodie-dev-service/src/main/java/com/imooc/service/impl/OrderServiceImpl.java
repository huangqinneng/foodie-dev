package com.imooc.service.impl;

import com.imooc.bo.ShopcartBO;
import com.imooc.bo.SubmitOrderBO;
import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.YesOrNo;
import com.imooc.mapper.OrderItemsMapper;
import com.imooc.mapper.OrderStatusMapper;
import com.imooc.mapper.OrdersMapper;
import com.imooc.pojo.*;
import com.imooc.service.AddressService;
import com.imooc.service.ItemService;
import com.imooc.service.OrderService;
import com.imooc.utils.DateUtil;
import com.imooc.vo.MerchantOrdersVO;
import com.imooc.vo.OrderVO;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderItemsMapper orderItemsMapper;

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Autowired
    private AddressService addressService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private Sid sid;

   /* @Autowired
    private RedisOperator redisOperator;*/

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public OrderVO createOrder(List<ShopcartBO> shopcartList, SubmitOrderBO submitOrderBO) {
        String userId = submitOrderBO.getUserId();
        String addressId = submitOrderBO.getAddressId();
        // 这里用规格作为了订单中商品的基本单位
        String itemSpecIds = submitOrderBO.getItemSpecIds();
        Integer payMethod = submitOrderBO.getPayMethod();
        String leftMsg = submitOrderBO.getLeftMsg();
        // 包邮费用设置为0
        Integer postAmount = 0;

        String orderId = sid.nextShort();

        UserAddress address = addressService.queryUserAddress(userId, addressId);
        // 1. 新订单数据保存
        Orders newOrder = new Orders();
        newOrder.setId(orderId);
        newOrder.setUserId(userId);
        // 设置收货地址快照
        newOrder.setReceiverName(address.getReceiver());
        newOrder.setReceiverMobile(address.getMobile());
        newOrder.setReceiverAddress(address.getProvince() + " "
                + address.getCity() + " "
                + address.getDistrict() + " "
                + address.getDetail() );
        //newOrder.setTotalAmount();  // 需要计算   放下面
        //newOrder.setRealPayAmount(); // 需要计算  放下面
        newOrder.setPostAmount(postAmount);
        newOrder.setPayMethod(payMethod);
        newOrder.setLeftMsg(leftMsg); // 买家留言
        newOrder.setIsComment(YesOrNo.NO.type);
        newOrder.setIsDelete(YesOrNo.NO.type);
        newOrder.setCreatedTime(new Date());
	    newOrder.setUpdatedTime(new Date());

        // 2.循环根据itemSpecIds保存订单商品信息
        String[] itemSpedIdArr = itemSpecIds.split(",");
        Integer totalAmount = 0;  // 总价格 /分
        Integer realPayAmount = 0;// 优惠后实际支付价格/分
        for (String itemSpecId : itemSpedIdArr) {

//            ShopcartBO shopcartBO = null;
            // TODO 整合redis后，商品购买的数量重新从redis的购物车中获取
           /* for (ShopcartBO tempShopcartBO : shopcartList) {
                if (tempShopcartBO.getSpecId().equals(itemSpecId)) {
                    shopcartBO = tempShopcartBO;
                    shopcartList.remove(shopcartBO);
                    break;
                }
            }*/
            // 2.1 根据规格id，查询规格具体信息
            ItemsSpec itemsSpec = itemService.queryItemSpecById(itemSpecId);

//            int buyCounts = shopcartBO.getBuyCounts();
            int buyCounts = 1;
            totalAmount += itemsSpec.getPriceNormal() * buyCounts;
            realPayAmount += itemsSpec.getPriceDiscount() * buyCounts;

            // 2.2 根据商品id，获取商品信息以及商品图片
            String itemId = itemsSpec.getItemId();
            Items item = itemService.queryItemById(itemId);
            String imgUrl = itemService.queryItemMainImgById(itemId);

            // 2.3 循环保存子订单数据到数据库
            String subOrderId = sid.nextShort();
            OrderItems subOrderItem = new OrderItems();
            subOrderItem.setId(subOrderId);
            subOrderItem.setOrderId(orderId);
            subOrderItem.setItemId(itemId);
            subOrderItem.setItemName(item.getItemName());
            subOrderItem.setItemImg(imgUrl);
            subOrderItem.setBuyCounts(buyCounts);
            subOrderItem.setItemSpecId(itemSpecId);
            subOrderItem.setItemSpecName(itemsSpec.getName());
            subOrderItem.setPrice(itemsSpec.getPriceDiscount());
            orderItemsMapper.insert(subOrderItem);

            // 2.4 在用户提交订单以后，规格表中需要扣除库存
            itemService.decreaseItemSpecStock(itemSpecId, buyCounts);
        }
        // 2.5 订单表中的总价更新
        newOrder.setTotalAmount(totalAmount);
        newOrder.setRealPayAmount(realPayAmount);
        ordersMapper.insert(newOrder);
        // 3. 保存订单状态表
        OrderStatus waitPayOrderStatus = new OrderStatus();
        waitPayOrderStatus.setOrderId(orderId);
        // 测试直接给20状态（支付成功）
//        waitPayOrderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        waitPayOrderStatus.setOrderStatus(OrderStatusEnum.WAIT_DELIVER.type);
        waitPayOrderStatus.setCreatedTime(new Date());
        orderStatusMapper.insert(waitPayOrderStatus);

        // 4. 构建商户订单，用于传给支付中心
        MerchantOrdersVO merchantOrdersVO = new MerchantOrdersVO();
        merchantOrdersVO.setMerchantOrderId(orderId);
        merchantOrdersVO.setMerchantUserId(userId);
        merchantOrdersVO.setAmount(realPayAmount + postAmount);
        merchantOrdersVO.setPayMethod(payMethod);

        // 5. 构建自定义订单VO
	    OrderVO orderVO = new OrderVO();
	    orderVO.setOrderId(orderId);
	    orderVO.setMerchantOrdersVO(merchantOrdersVO);

        return orderVO;

    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateOrderStatus(String orderId, Integer orderStatus) {
        OrderStatus paidStatus = new OrderStatus();
        paidStatus.setOrderId(orderId);
        paidStatus.setOrderStatus(orderStatus);
        paidStatus.setPayTime(new Date());

        orderStatusMapper.updateByPrimaryKeySelective(paidStatus);
    }
	@Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public OrderStatus queryOrderStatusInfo(String orderId) {
        return orderStatusMapper.selectByPrimaryKey(orderId);
    }

	@Override
	public void closeOrder() {
		// 查询所有未支付的订单，判断是否超时（1天），超时则关闭交易
		OrderStatus orderStatus = new OrderStatus();
		orderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
		List<OrderStatus> orderStatusList = orderStatusMapper.select(orderStatus);
		for (OrderStatus order:orderStatusList){
			Date createdTime = order.getCreatedTime();
			int days = DateUtil.daysBetween(createdTime, new Date());
			if (days >= 1){
				// 超过1天，关闭订单
				this.doCloseOrder(order.getOrderId());
			}
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	void doCloseOrder(String orderId){
		OrderStatus orderStatus = new OrderStatus();
		orderStatus.setOrderId(orderId);
		orderStatus.setOrderStatus(OrderStatusEnum.CLOSE.type);
		orderStatus.setCreatedTime(new Date());
		int i = orderStatusMapper.updateByPrimaryKey(orderStatus);
		System.out.println("===========关闭订单============" + orderId);
	}
}
