package com.imooc.config;

import com.imooc.service.OrderService;
import com.imooc.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Auther: huangqn
 * @Date: 2020/11/30 16:32
 * @Description:
 */
@Component
public class OrderJob {

	@Autowired
	private OrderService orderService;

	/**
	 * 使用定时任务关闭超时未支付订单，会存在弊端
	 * 1. 会有时差，程序不严谨
	 *      10:39下单，11：:00 检查不超过1小时，12：:00检查超过1小时
	 * 2. 定时任务，不支持集群，es job解决
	 * 3. 会对数据库全表搜索，及其影响性能
	 *
	 * 定时任务，仅仅只适用于小型轻量级项目，传统项目
	 *
	 * 后续可以根据消息队列解决MQ
	 * 使用延时队列
	 */

	@Scheduled(cron = "0/3 * * * * ?")
	public void autoCloseOrder(){
		System.out.println(DateUtil.dateToStringWithTime() + " 。。定时任务--开始。。。。。。" );
		orderService.closeOrder();
		System.out.println(DateUtil.dateToStringWithTime() + " 。。定时任务--结束。。。。。。" );
	}
}
