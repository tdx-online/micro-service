package com.hitwh.userservice.service;


import com.hitwh.userservice.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    /**
     * 根据用户 ID 获取购物车信息
     */
    List<ShoppingCart> getShoppingCartByUserId(Integer uid);

    Boolean addGoods(ShoppingCart shoppingCart);

    Boolean deleteGoods(int id);
    Boolean deleteGoodsList(List<Integer> ids);

    Boolean alterGoodNumber(int id, int count);

    /**
     * 获取购物车商品数量
     *
     * @param id 用户 ID
     * @return integer
     */
    Integer getShoppingCartCount(int id);

    Boolean checkCartStatus(int pid, int uid);
}
