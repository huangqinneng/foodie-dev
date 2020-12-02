package com.imooc.vo;

/**
 * @Auther: huangqn
 * @Date: 2020/12/2 10:40
 * @Description:
 */
public class MySubOrderItemVO {

	public String itemId;
	public String itemImg;
	public String itemSpecId;
	public String itemSpecName;
	public Integer buyCounts;
	public Integer price;

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemImg() {
		return itemImg;
	}

	public void setItemImg(String itemImg) {
		this.itemImg = itemImg;
	}

	public String getItemSpecId() {
		return itemSpecId;
	}

	public void setItemSpecId(String itemSpecId) {
		this.itemSpecId = itemSpecId;
	}

	public String getItemSpecName() {
		return itemSpecName;
	}

	public void setItemSpecName(String itemSpecName) {
		this.itemSpecName = itemSpecName;
	}

	public Integer getBuyCounts() {
		return buyCounts;
	}

	public void setBuyCounts(Integer buyCounts) {
		this.buyCounts = buyCounts;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
}
