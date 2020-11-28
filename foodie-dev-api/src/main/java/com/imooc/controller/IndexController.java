package com.imooc.controller;

import com.imooc.enums.YesOrNo;
import com.imooc.pojo.Carousel;
import com.imooc.pojo.Category;
import com.imooc.service.CarouselService;
import com.imooc.service.CategoryService;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.vo.CategoryVO;
import com.imooc.vo.NewItemsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: huangqn
 * @Date: 2020/11/25 17:13
 * @Description:
 */
@Api(value = "首页展示",tags = {"首页展示相关的接口"})
@RestController
@RequestMapping("index")
public class IndexController {

	@Autowired
	private CarouselService carouselService;
	@Autowired
	private CategoryService categoryService;


	@ApiOperation(value = "获取首页轮播图列表",notes = "获取首页轮播图列表",httpMethod = "GET")
	@GetMapping("/carousel")
	public IMOOCJSONResult carousel(){
		List<Carousel> carousels = carouselService.queryAll(YesOrNo.YES.type);
		return IMOOCJSONResult.ok(carousels);
	}

	/**
	 * 首页分页展示需求
	 * 1. 第一次刷新主页，查询大分类
	 * 2. 鼠标移动到大分类下，进行一次加载，如果已经加载过，则不需要重新加载（懒加载）
	 */
	@ApiOperation(value = "获取商品分类（一级分类）",notes = "获取商品分类（一级分类）",httpMethod = "GET")
	@GetMapping("/cats")
	public IMOOCJSONResult cats(){
		List<Category> categories = categoryService.queryAllRootLevelCat();
		return IMOOCJSONResult.ok(categories);
	}
	@ApiOperation(value = "获取商品子分类",notes = "获取商品子分类",httpMethod = "GET")
	@GetMapping("/subCat/{rootCatId}")
	public IMOOCJSONResult subCat(
			@ApiParam(name = "rootCatId",value = "一级分类id",required = true)
			@PathVariable Integer rootCatId){
		if (rootCatId == null){
			return IMOOCJSONResult.errorMsg("分类不存在");
		}
		List<CategoryVO> subCatList = categoryService.getSubCatList(rootCatId);
		return IMOOCJSONResult.ok(subCatList);
	}

	@ApiOperation(value = "查询每一个一级分类下的最新6条商品数据",
			notes = "查询每一个一级分类下的最新6条商品数据",httpMethod = "GET")
	@GetMapping("/sixNewItems/{rootCatId}")
	public IMOOCJSONResult sixNewItems(
			@ApiParam(name = "rootCatId",value = "一级分类id",required = true)
			@PathVariable Integer rootCatId){
		if (rootCatId == null){
			return IMOOCJSONResult.errorMsg("分类不存在");
		}
		List<NewItemsVO> sixNewItemsLazy = categoryService.getSixNewItemsLazy(rootCatId);
		return IMOOCJSONResult.ok(sixNewItemsLazy);
	}
}
