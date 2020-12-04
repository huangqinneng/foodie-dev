package com.imooc.mapper;

import com.imooc.my.mapper.MyMapper;
import com.imooc.pojo.ItemsComments;
import com.imooc.vo.MyCommentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsCommentsMapper extends MyMapper<ItemsComments> {

	/**
	 * 保存评价
	 * @param paramsMap
	 */
	void saveComments(@Param("paramsMap") Map<String,Object> paramsMap);

	/**
	 * 根据用户Id查询我的评价
	 * @param paramsMap
	 * @return
	 */
	List<MyCommentVO> queryMyComments(@Param("paramsMap") Map<String,Object> paramsMap);
}