package com.hitwh.productservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCart {
    private Integer id;
    private Integer pid; // 商品id
    private Integer uid; // 用户id
    private Integer count; // 商品数量
    private Integer status; // 商品状态
    private Product product; // 商品信息
    private ProductImage image; // 商品图片
}
