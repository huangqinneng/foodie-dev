package com.imooc.vo;

import java.util.List;

/**
 * 最新商品VO
 */
public class NewItemsVO {
    /**
     * 主键
     */
    private Integer rootCatId;

    /**
     * 分类名称
     */
    private String rootCatName;

    /**
     * 分类类型
     */
    private String slogan;

    /**
     * 父id
     */
    private String catImage;

    private String bgColor;

    List<SimpleItemVO> simpleItemList;


	public Integer getRootCatId() {
		return rootCatId;
	}

	public void setRootCatId(Integer rootCatId) {
		this.rootCatId = rootCatId;
	}

	public String getRootCatName() {
		return rootCatName;
	}

	public void setRootCatName(String rootCatName) {
		this.rootCatName = rootCatName;
	}

	public String getSlogan() {
		return slogan;
	}

	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}

	public String getCatImage() {
		return catImage;
	}

	public void setCatImage(String catImage) {
		this.catImage = catImage;
	}

	public String getBgColor() {
		return bgColor;
	}

	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

	public List<SimpleItemVO> getSimpleItemList() {
		return simpleItemList;
	}

	public void setSimpleItemList(List<SimpleItemVO> simpleItemList) {
		this.simpleItemList = simpleItemList;
	}
}