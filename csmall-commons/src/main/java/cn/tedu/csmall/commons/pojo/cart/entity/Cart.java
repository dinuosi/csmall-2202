package cn.tedu.csmall.commons.pojo.cart.entity;

import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;

@Data
public class Cart implements Serializable {
    private Integer id;
    // 商品编号
    private String commodityCode;
    // 价格
    private Integer price;
    // 数量
    private Integer count;
    // 用户id
    private String userId;

}
