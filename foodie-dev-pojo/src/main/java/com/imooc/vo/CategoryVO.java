package com.imooc.vo;

import java.util.List;

public class CategoryVO {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 分类类型
     */
    private Integer type;

    /**
     * 父id
     */
    private Integer fatherId;

    List<SubCategoryVO> subCatList;

	public List<SubCategoryVO> getSubCatList() {
		return subCatList;
	}

	public void setSubCatList(List<SubCategoryVO> subCatList) {
		this.subCatList = subCatList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getFatherId() {
		return fatherId;
	}

	public void setFatherId(Integer fatherId) {
		this.fatherId = fatherId;
	}


}