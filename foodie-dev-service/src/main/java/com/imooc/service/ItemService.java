package com.imooc.service;

import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsImg;
import com.imooc.pojo.ItemsParam;
import com.imooc.pojo.ItemsSpec;
import com.imooc.utils.PagedGridResult;
import com.imooc.vo.CommentLevelCountsVO;
import com.imooc.vo.ShopcartVO;

import java.util.List;

/**
 * @Auther: huangqn
 * @Date: 2020/11/28 15:28
 * @Description: 商品相关
 */
public interface ItemService {
	/**
	 * 根据商品id查询 详情
	 * @param itemId
	 * @return
	 */
	Items queryItemById(String itemId);

	/**
	 * 根据商品id查询 商品图片列表
	 * @param itemId
	 * @return
	 */
	List<ItemsImg> queryItemImgList(String itemId);

	/**
	 * 根据商品id查询 商品规格
	 * @param itemId
	 * @return
	 */
	List<ItemsSpec> queryItemSpecList(String itemId);

	/**
	 * 根据商品id查询 商品参数
	 * @param itemId
	 * @return
	 */
	ItemsParam queryItemParam(String itemId);

	/**
	 * 根据商品id查询 商品评价等级数量
	 * @param itemId
	 * @return
	 */
	CommentLevelCountsVO queryCommentCounts(String itemId);

	/**
	 * 根据商品ID和等级查询评价
	 * @param itemId
	 * @param level
	 * @return
	 */
	PagedGridResult queryPagedComments(String itemId, Integer level,
									   Integer page, Integer pageSize);

	/**
	 * 搜索商品详情
	 * @param keywords
	 * @param sort
	 * @param page
	 * @param pageSize
	 * @return
	 */
	PagedGridResult searchItems(String keywords, String sort,
								Integer page,Integer pageSize);

	/**
	 * 根据商品ID 搜索商品
	 * @param catId
	 * @param sort
	 * @param page
	 * @param pageSize
	 * @return
	 */
	PagedGridResult searchItemsByThirdCat(Integer catId, String sort,
										  Integer page,Integer pageSize);

	/**
	 * 根据规格ids 查询最新购物车中商品数据（用于渲染购物车中商品数据）
	 * @param specIds
	 * @return
	 */
	List<ShopcartVO>  queryItemBySpecIds(String specIds);

	/**
	 * 根据商品规格id查询具体规格信息
	 * @param specId
	 * @return
	 */
	ItemsSpec queryItemSpecById(String specId);

	String queryItemMainImgById(String itemId);

	/**
	 * 减库存
	 */
	void decreaseItemSpecStock(String itemSpecId, int buyCounts);
}
