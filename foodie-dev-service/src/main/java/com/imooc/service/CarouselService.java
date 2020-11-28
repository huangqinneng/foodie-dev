package com.imooc.service;

import com.imooc.pojo.Carousel;

import java.util.List;

/**
 * @Auther: huangqn
 * @Date: 2020/11/25 16:28
 * @Description: 轮播图片相关服务类
 */
public interface CarouselService {
	/**
	 * 查询所有轮播图
	 * @param isShow
	 * @return
	 */
	List<Carousel> queryAll(Integer isShow);
}
