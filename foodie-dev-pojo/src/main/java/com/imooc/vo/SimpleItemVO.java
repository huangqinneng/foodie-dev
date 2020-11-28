package com.imooc.vo;

/**
 * 最新商品VO
 */
public class SimpleItemVO {
    /**
     * 主键
     */
    private String itemId;

    /**
     * 分类名称
     */
    private String itemName;

    /**
     * 分类类型
     */
    private String itemUrl;

    /**
     * 父id
     */
    private String createdTime;


	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemUrl() {
		return itemUrl;
	}

	public void setItemUrl(String itemUrl) {
		this.itemUrl = itemUrl;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
}