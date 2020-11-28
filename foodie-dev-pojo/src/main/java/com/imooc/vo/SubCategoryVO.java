package com.imooc.vo;

public class SubCategoryVO {


	/**
	 * 主键
	 */
	private Integer subId;

	/**
	 * 分类名称
	 */
	private String subName;

	/**
	 * 分类类型
	 */
	private Integer subType;

	/**
	 * 父id
	 */
	private Integer subFatherId;


	public Integer getSubId() {
		return subId;
	}

	public void setSubId(Integer subId) {
		this.subId = subId;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public Integer getSubType() {
		return subType;
	}

	public void setSubType(Integer subType) {
		this.subType = subType;
	}

	public Integer getSubFatherId() {
		return subFatherId;
	}

	public void setSubFatherId(Integer subFatherId) {
		this.subFatherId = subFatherId;
	}
}