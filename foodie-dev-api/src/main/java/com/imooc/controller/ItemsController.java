package com.imooc.controller;

import com.imooc.enums.YesOrNo;
import com.imooc.pojo.*;
import com.imooc.service.CarouselService;
import com.imooc.service.CategoryService;
import com.imooc.service.ItemService;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.PagedGridResult;
import com.imooc.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: huangqn
 * @Date: 2020/11/25 17:13
 * @Description:
 */
@Api(value = "商品信息",tags = {"商品信息展示相关的接口"})
@RestController
@RequestMapping("items")
public class ItemsController extends BaseController{

	@Autowired
	private ItemService itemService;


	@ApiOperation(value = "查询商品详情",notes = "查询商品详情",httpMethod = "GET")
	@GetMapping("/info/{itemId}")
	public IMOOCJSONResult info(
			@ApiParam(name = "itemId",value = "商品Id",required = true)
			@PathVariable String itemId){
		if (StringUtils.isBlank(itemId)){
			IMOOCJSONResult.errorMsg("商品Id不存在");
		}
		Items items = itemService.queryItemById(itemId);
		List<ItemsImg> itemsImgs = itemService.queryItemImgList(itemId);
		List<ItemsSpec> itemsSpecs = itemService.queryItemSpecList(itemId);
		ItemsParam itemsParam = itemService.queryItemParam(itemId);

		ItemInfoVO itemInfoVO = new ItemInfoVO();
		itemInfoVO.setItem(items);
		itemInfoVO.setItemImgList(itemsImgs);
		itemInfoVO.setItemSpecList(itemsSpecs);
		itemInfoVO.setItemParams(itemsParam);
		return IMOOCJSONResult.ok(itemInfoVO);
	}

	@ApiOperation(value = "查询商品评价等级",notes = "查询商品评价等级",httpMethod = "GET")
	@GetMapping("/commentLevel")
	public IMOOCJSONResult commentLevel(
			@ApiParam(name = "itemId",value = "商品Id",required = true)
			@RequestParam String itemId){
		if (StringUtils.isBlank(itemId)){
			IMOOCJSONResult.errorMsg("商品Id不存在");
		}
		CommentLevelCountsVO countsVO = itemService.queryCommentCounts(itemId);
		return IMOOCJSONResult.ok(countsVO);
	}

	@ApiOperation(value = "查询商品评价内容",notes = "查询商品评价内容",httpMethod = "GET")
	@GetMapping("/comments")
	public IMOOCJSONResult comments(
			@ApiParam(name = "itemId",value = "商品Id",required = true)
			@RequestParam String itemId,
			@ApiParam(name = "level",value = "评价等级",required = false)
			@RequestParam Integer level,
			@ApiParam(name = "page",value = "下一页的页数",required = false)
			@RequestParam Integer page,
			@ApiParam(name = "pageSize",value = "每页显示条数",required = false)
			@RequestParam Integer pageSize
	){
		if (StringUtils.isBlank(itemId)){
			IMOOCJSONResult.errorMsg("商品Id不存在");
		}
		if (page == null){
			page = 1;
		}
		if (pageSize == null){
			pageSize = COMMENT_PAGE_SIZE;
		}
		PagedGridResult pagedGridResult = itemService.queryPagedComments(itemId, level, page, pageSize);
		return IMOOCJSONResult.ok(pagedGridResult);
	}

	@ApiOperation(value = "搜索商品列表",notes = "搜索商品列表",httpMethod = "GET")
	@GetMapping("/search")
	public IMOOCJSONResult search(
			@ApiParam(name = "keywords",value = "搜索关键字",required = true)
			@RequestParam String keywords,
			@ApiParam(name = "sort",value = "排序类别",required = false)
			@RequestParam String sort,
			@ApiParam(name = "page",value = "下一页的页数",required = false)
			@RequestParam Integer page,
			@ApiParam(name = "pageSize",value = "每页显示条数",required = false)
			@RequestParam Integer pageSize
	){
		if (page == null){
			page = 1;
		}
		if (pageSize == null){
			pageSize = PAGE_SIZE;
		}
		PagedGridResult pagedGridResult = itemService.searchItems(keywords, sort, page, pageSize);
		return IMOOCJSONResult.ok(pagedGridResult);
	}

	@ApiOperation(value = "根据商品ID搜索商品列表",notes = "根据商品ID搜索商品列表",httpMethod = "GET")
	@GetMapping("/catItems")
	public IMOOCJSONResult catItems(
			@ApiParam(name = "catId",value = "三级分类Id",required = true)
			@RequestParam Integer catId,
			@ApiParam(name = "sort",value = "排序类别",required = false)
			@RequestParam String sort,
			@ApiParam(name = "page",value = "下一页的页数",required = false)
			@RequestParam Integer page,
			@ApiParam(name = "pageSize",value = "每页显示条数",required = false)
			@RequestParam Integer pageSize
	){
		if (page == null){
			page = 1;
		}
		if (pageSize == null){
			pageSize = PAGE_SIZE;
		}
		PagedGridResult pagedGridResult = itemService.searchItemsByThirdCat(catId, sort, page, pageSize);
		return IMOOCJSONResult.ok(pagedGridResult);
	}

	// 用于用户长时间未登录网站，刷新购物车中的数据（主要是商品价格），类似于京东
	@ApiOperation(value = "根据规格Ids查询最新商品数据",notes = "根据规格Ids查询最新商品数据",httpMethod = "GET")
	@GetMapping("/refresh")
	public IMOOCJSONResult refresh(
			@ApiParam(name = "itemSpecIds",value = "拼接规格的Ids",required = true
				,example = "1001,1002,1003")
			@RequestParam String itemSpecIds
	){
		if (StringUtils.isBlank(itemSpecIds)){
			return IMOOCJSONResult.errorMsg("商品规格Ids不能为空");
		}
		List<ShopcartVO> shopcartVOS = itemService.queryItemBySpecIds(itemSpecIds);
		return IMOOCJSONResult.ok(shopcartVOS);
	}
}
