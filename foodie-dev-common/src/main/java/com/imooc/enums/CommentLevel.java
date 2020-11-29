package com.imooc.enums;

/**
 * @Auther: huangqn
 * @Date: 2020/11/28 21:35
 * @Description: 商品评价等级枚举
 */
public enum CommentLevel {

	GOOD(1, "好评"),
	NORMAL(2, "中评"),
	BAD(3, "差评");


	public final Integer type;
	public final String value;

	CommentLevel(Integer type, String value) {
		this.type = type;
		this.value = value;
	}

}
