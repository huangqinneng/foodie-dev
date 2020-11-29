package com.imooc.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 2020/11/29
 *
 */
@ApiModel(value = "用户请求购物车BO",description = "从客户端，由用户传入的数据封装在此entity中")
public class ShopcartBO {
    @ApiModelProperty(value = "商品ID", name = "itemId", example = "imooc", required = true)
    private String itemId;
    @ApiModelProperty(value = "商品图片url", name = "itemImgUrl", example = "imooc", required = true)
    private String itemImgUrl;
    @ApiModelProperty(value = "商品名称", name = "itemName", example = "蛋糕", required = true)
    private String itemName;
    @ApiModelProperty(value = "规格Id", name = "specId", example = "imooc", required = true)
    private String specId;
    @ApiModelProperty(value = "规格名称", name = "specName", example = "imooc", required = true)
    private String specName;
    @ApiModelProperty(value = "购物车数量", name = "buyCounts", example = "1", required = true)
    private Integer buyCounts;
    @ApiModelProperty(value = "折扣价格", name = "priceDiscount", example = "10", required = true)
    private String priceDiscount;
    @ApiModelProperty(value = "没折扣价格", name = "priceNormal", example = "20", required = true)
    private String priceNormal;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemImgUrl() {
        return itemImgUrl;
    }

    public void setItemImgUrl(String itemImgUrl) {
        this.itemImgUrl = itemImgUrl;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getSpecId() {
        return specId;
    }

    public void setSpecId(String specId) {
        this.specId = specId;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public Integer getBuyCounts() {
        return buyCounts;
    }

    public void setBuyCounts(Integer buyCounts) {
        this.buyCounts = buyCounts;
    }

    public String getPriceDiscount() {
        return priceDiscount;
    }

    public void setPriceDiscount(String priceDiscount) {
        this.priceDiscount = priceDiscount;
    }

    public String getPriceNormal() {
        return priceNormal;
    }

    public void setPriceNormal(String priceNormal) {
        this.priceNormal = priceNormal;


    }
    @Override
    public String toString() {
        return "ShopcartBO{" +
                "itemId='" + itemId + '\'' +
                ", itemImgUrl='" + itemImgUrl + '\'' +
                ", itemName='" + itemName + '\'' +
                ", specId='" + specId + '\'' +
                ", specName='" + specName + '\'' +
                ", buyCounts=" + buyCounts +
                ", priceDiscount='" + priceDiscount + '\'' +
                ", priceNormal='" + priceNormal + '\'' +
                '}';
    }
}
