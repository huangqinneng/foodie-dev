package com.imooc.mapper;

import com.imooc.my.mapper.MyMapper;
import com.imooc.pojo.Category;
import com.imooc.vo.CategoryVO;
import com.imooc.vo.NewItemsVO;

import java.util.List;
import java.util.Map;

public interface CategoryMapper extends MyMapper<Category> {

	/**
	 * 查询二三级分类
	 * @param rootCatId
	 * @return
	 */
	List<CategoryVO> getSubCatList(Integer rootCatId);

	List<NewItemsVO> getSixNewItemsLazy(Map<String,Object> map);

}