package com.imooc.enums;

/**
 * @Auther: huangqn
 * @Date: 2020/11/23 11:28
 * @Description: 性别
 */
public enum Sex {

	WOMAN(0, "女"),
	MAN(1, "男"),
	SECRET(2, "保密");

	public final Integer type;
	public final String value;

	Sex(Integer type, String value) {
		this.type = type;
		this.value = value;
	}

}
