package com.imooc.enums;

/**
 * @Auther: huangqn
 * @Date: 2020/11/26 20:32
 * @Description: 分类枚举
 */
public enum CategoryEnum {

	ONE(1,"1级分类"),
	TOW(2,"2级分类"),
	THREE(3,"3级分类");

	public final Integer type;
	public final String value;

	CategoryEnum(Integer type,String value){
		this.type = type;
		this.value = value;
	}
}
