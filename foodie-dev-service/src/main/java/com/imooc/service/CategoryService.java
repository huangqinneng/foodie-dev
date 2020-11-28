package com.imooc.service;

import com.imooc.pojo.Category;
import com.imooc.vo.CategoryVO;
import com.imooc.vo.NewItemsVO;

import java.util.List;

/**
 * @Auther: huangqn
 * @Date: 2020/11/25 16:28
 * @Description: 主页菜单
 */
public interface CategoryService {
	/**
	 * 查询所有1级分类
	 * @return
	 */
	List<Category> queryAllRootLevelCat();

	/**
	 * 查询二三级分类
	 * @return
	 */
	List<CategoryVO> getSubCatList(Integer rootCatId);

	/**
	 * 查询最新6个商品分类
	 * @return
	 */
	List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId);
}
