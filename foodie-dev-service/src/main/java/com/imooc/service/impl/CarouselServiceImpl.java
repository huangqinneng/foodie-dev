package com.imooc.service.impl;

import com.imooc.mapper.CarouselMapper;
import com.imooc.pojo.Carousel;
import com.imooc.service.CarouselService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Auther: huangqn
 * @Date: 2020/11/25 16:30
 * @Description:
 */
@Service
public class CarouselServiceImpl implements CarouselService {
	private static final Logger log = LoggerFactory.getLogger(CarouselServiceImpl.class);

	@Autowired
	private CarouselMapper carouselMapper;

	@Override
	public List<Carousel> queryAll(Integer isShow) {
		Example example = new Example(Carousel.class);
		example.orderBy("sort").asc();
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("isShow", isShow);
		List<Carousel> carousels = carouselMapper.selectByExample(example);
		log.info("");
		return carousels;
	}
}
