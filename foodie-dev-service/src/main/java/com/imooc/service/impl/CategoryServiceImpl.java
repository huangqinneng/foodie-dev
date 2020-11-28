package com.imooc.service.impl;

import com.imooc.enums.CategoryEnum;
import com.imooc.mapper.CategoryMapper;
import com.imooc.pojo.Category;
import com.imooc.service.CategoryService;
import com.imooc.vo.CategoryVO;
import com.imooc.vo.NewItemsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: huangqn
 * @Date: 2020/11/26 20:25
 * @Description:
 */
@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryMapper categoryMapper;

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public List<Category> queryAllRootLevelCat() {
		Example example = new Example(Category.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("type", CategoryEnum.ONE.type);
		List<Category> categories = categoryMapper.selectByExample(example);
		return categories;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public List<CategoryVO> getSubCatList(Integer rootCatId) {
		List<CategoryVO> subCatList = categoryMapper.getSubCatList(rootCatId);
		return subCatList;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId) {
		Map<String,Object> map = new HashMap<>();
		map.put("rootCatId",rootCatId);
		return categoryMapper.getSixNewItemsLazy(map);
	}
}
