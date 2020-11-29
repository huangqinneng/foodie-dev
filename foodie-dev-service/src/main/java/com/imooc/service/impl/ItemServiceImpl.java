package com.imooc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.enums.CommentLevel;
import com.imooc.mapper.*;
import com.imooc.pojo.*;
import com.imooc.service.ItemService;
import com.imooc.utils.DesensitizationUtil;
import com.imooc.utils.PagedGridResult;
import com.imooc.vo.CommentLevelCountsVO;
import com.imooc.vo.ItemCommentVO;
import com.imooc.vo.SearchItemsVO;
import com.imooc.vo.ShopcartVO;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Service
public class ItemServiceImpl implements ItemService {
    private final Logger log = LoggerFactory.getLogger(ItemServiceImpl.class);

    @Autowired
    private ItemsMapper itemsMapper;
    @Autowired
    private ItemsImgMapper itemsImgMapper;
    @Autowired
    private ItemsSpecMapper itemsSpecMapper;
    @Autowired
    private ItemsParamMapper itemsParamMapper;
    @Autowired
    private ItemsCommentsMapper itemsCommentsMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Items queryItemById(String itemId) {
        Items items = itemsMapper.selectByPrimaryKey(itemId);
        log.info("根据商品ID：{},查询出商品内容：{}",itemId, JSONObject.toJSONString(items));
        return items;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsImg> queryItemImgList(String itemId) {
        Example example = new Example(ItemsImg.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId",itemId);
        List<ItemsImg> itemsImgs = itemsImgMapper.selectByExample(example);
        log.info("根据商品ID：{},查询商品图片列表：{}",itemId, JSONObject.toJSONString(itemsImgs));
        return itemsImgs;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsSpec> queryItemSpecList(String itemId) {
        Example example = new Example(ItemsImg.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId",itemId);
        List<ItemsSpec> itemsSpecs = itemsSpecMapper.selectByExample(example);
        log.info("根据商品ID：{},查询出商品规格：{}",itemId, JSONObject.toJSONString(itemsSpecs));
        return itemsSpecs;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ItemsParam queryItemParam(String itemId) {
        Example example = new Example(ItemsImg.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId",itemId);
        ItemsParam itemsParams = itemsParamMapper.selectOneByExample(example);
        log.info("根据商品ID：{},查询出商品参数：{}",itemId, JSONObject.toJSONString(itemsParams));
        return itemsParams;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public CommentLevelCountsVO queryCommentCounts(String itemId) {
        Integer goodCounts = this.getCommentCounts(itemId, CommentLevel.GOOD.type);
        Integer normalCounts = this.getCommentCounts(itemId, CommentLevel.NORMAL.type);
        Integer badCounts = this.getCommentCounts(itemId, CommentLevel.BAD.type);
        Integer totalCounts = goodCounts + normalCounts + badCounts;
        CommentLevelCountsVO countsVO = new CommentLevelCountsVO();
        countsVO.setTotalCounts(totalCounts);
        countsVO.setGoodCounts(goodCounts);
        countsVO.setNormalCounts(normalCounts);
        countsVO.setBadCounts(badCounts);
        return countsVO;
    }

//    @Transactional(propagation = Propagation.SUPPORTS)
    private Integer getCommentCounts(String itemId,Integer level){
        ItemsComments comments = new ItemsComments();
        comments.setItemId(itemId);
        if (level != null){
            comments.setCommentLevel(level);
        }
        int count = itemsCommentsMapper.selectCount(comments);
        return count;
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryPagedComments(String itemId, Integer level,
                                                  Integer page,Integer pageSize) {
        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("itemId",itemId);
        paramsMap.put("level",level);
        // mybatis - pagehelper

        PageHelper.startPage(page,pageSize);
        List<ItemCommentVO> itemCommentVOS = itemsMapper.queryItemComments(paramsMap);
        for (ItemCommentVO vo:itemCommentVOS){
            vo.setNickname(DesensitizationUtil.commonDisplay(vo.getNickname()));
        }
        return this.setterPagedGrid(itemCommentVOS,page);
    }

    private PagedGridResult setterPagedGrid(List<?> list,Integer page){
        PageInfo<?> pageList = new PageInfo<>(list);
        PagedGridResult grid = new PagedGridResult();
        grid.setPage(page);
        grid.setRows(list);
        grid.setTotal(pageList.getPages());
        grid.setRecords(pageList.getTotal());
        return grid;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult searchItems(String keywords, String sort,
                                              Integer page,Integer pageSize) {
        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("keywords",keywords);
        paramsMap.put("sort",sort);
        // mybatis - pagehelper
        PageHelper.startPage(page,pageSize);
        List<SearchItemsVO> searchItemsVOS = itemsMapper.searchItems(paramsMap);

        return this.setterPagedGrid(searchItemsVOS,page);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult searchItemsByThirdCat(Integer catId, String sort,
                                              Integer page,Integer pageSize) {
        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("catId",catId);
        paramsMap.put("sort",sort);
        // mybatis - pagehelper
        PageHelper.startPage(page,pageSize);
        List<SearchItemsVO> searchItemsVOS = itemsMapper.searchItemsByThirdCat(paramsMap);

        return this.setterPagedGrid(searchItemsVOS,page);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ShopcartVO> queryItemBySpecIds(String specIds) {
        String[] ids = specIds.split(",");
        List<String> specIdsList = new ArrayList<>();
        Collections.addAll(specIdsList,ids);
        List<ShopcartVO> shopcartVOS = itemsMapper.queryItemBySpecIds(specIdsList);
        return shopcartVOS;
    }

}
