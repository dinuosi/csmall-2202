package cn.tedu.csmall.commons.pojo.stock.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Stock implements Serializable {
    private Integer id;
    private String commodityCode;
    private Integer reduceCount;
}
