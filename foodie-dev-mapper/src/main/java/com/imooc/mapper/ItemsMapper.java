package com.imooc.mapper;

import com.imooc.my.mapper.MyMapper;
import com.imooc.pojo.Items;
import com.imooc.vo.ItemCommentVO;
import com.imooc.vo.SearchItemsVO;
import com.imooc.vo.ShopcartVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsMapper extends MyMapper<Items> {


    /**
     * 根据商品ID和等级查询评价
     * @param paramsMap
     * @return
     */
    List<ItemCommentVO> queryItemComments(
            @Param(value = "paramsMap") Map<String,Object> paramsMap);

    /**
     *搜索商品列表
     * @param paramsMap
     * @return
     */
    List<SearchItemsVO> searchItems(
            @Param(value = "paramsMap") Map<String,Object> paramsMap);
    /**
     *搜索商品列表
     * @param paramsMap
     * @return
     */
    List<SearchItemsVO> searchItemsByThirdCat(
            @Param(value = "paramsMap") Map<String,Object> paramsMap);

    /**
     * 根据商品规格Id查询商品信息
     * @param paramsList
     * @return
     */
    List<ShopcartVO> queryItemBySpecIds(@Param("paramsList") List<String> paramsList);
}