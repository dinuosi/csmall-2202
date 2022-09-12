package cn.tedu.csmall.commons.pojo.cart.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("购物车新增DTO")
@Data
public class CartAddDTO implements Serializable {
    @ApiModelProperty(value = "商品编号",name = "commodityCode",example = "PC100")
    private String commodityCode;
    @ApiModelProperty(value = "商品单价",name = "price",example = "188")
    private Integer price;
    @ApiModelProperty(value = "商品个数",name = "count",example = "5")
    private Integer count;
    @ApiModelProperty(value = "用户ID",name = "userId",example = "UU100")
    private String userId;
}




