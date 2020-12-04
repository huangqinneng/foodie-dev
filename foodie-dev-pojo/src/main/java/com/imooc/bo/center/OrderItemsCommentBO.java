package com.imooc.bo.center;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "用户中心-评价商品",description = "从客户端，由用户传入的数据封装在此entity中")
public class OrderItemsCommentBO {
    /**
     *
     */
    @NotBlank(message = "评论Id不能为空")
    @ApiModelProperty(value = "评论Id",name = "commentId",example = "imoocwq123123",required = true)
    private String commentId;

    /**
     * 商品id
     */
    @NotBlank(message = "商品Id不能为空")
    @ApiModelProperty(value = "商品Id",name = "itemId",example = "imooc213123",required = true)
    private String itemId;

    /**
     * 商品名称
     */
    @NotBlank(message = "商品名称不能为空")
    @ApiModelProperty(value = "商品名称",name = "itemName",example = "马卡龙",required = true)
    private String itemName;

    /**
     * 规格id
     */
    @NotBlank(message = "规格Id不能为空")
    @ApiModelProperty(value = "规格Id",name = "itemSpecId",example = "imooc4564",required = true)
    private String itemSpecId;

    /**
     * 规格名称
     */
    @NotBlank(message = "规格名称不能为空")
    @ApiModelProperty(value = "规格名称",name = "itemSpecName",example = "巧克力味",required = true)
    private String itemSpecName;

    /**
     * 评价等级
     */
    @NotBlank(message = "评价等级不能为空")
    @ApiModelProperty(value = "评价等级",name = "commentLevel",example = "1",required = true)
    private Integer commentLevel;

    /**
     * 评价内容
     */
    @ApiModelProperty(value = "评论内容",name = "content",example = "imooc",required = true)
    private String content;


	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

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

	public Integer getCommentLevel() {
		return commentLevel;
	}

	public void setCommentLevel(Integer commentLevel) {
		this.commentLevel = commentLevel;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "OrderItemsCommentBO{" +
				"commentLevel=" + commentLevel +
				", content=" + content +
				'}';
	}
}