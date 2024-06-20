package com.hitwh.userservice.service.impl;

import com.hitwh.userservice.entity.ShoppingCart;
import com.hitwh.userservice.mapper.ProductMapper;
import com.hitwh.userservice.mapper.ShoppingCartMapper;
import com.hitwh.userservice.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartMapper shoppingCartMapper;
    private final ProductMapper productMapper;

    @Autowired
    public ShoppingCartServiceImpl(ShoppingCartMapper shoppingCartMapper, ProductMapper productMapper) {
        this.shoppingCartMapper = shoppingCartMapper;
        this.productMapper = productMapper;
    }

    @Override
    public List<ShoppingCart> getShoppingCartByUserId(Integer uid) {
        List<ShoppingCart> shoppingCarts = shoppingCartMapper.queryByUserId(uid);
        shoppingCarts.forEach(cart -> {
            cart.setProduct(productMapper.queryById(cart.getPid()));
            cart.setImage(productMapper.getImageByProductId(cart.getPid()));
        });
        return shoppingCarts;
    }

    @Override
    public Integer getShoppingCartCount(int id) {
        return shoppingCartMapper.getCount(id);
    }

    @Override
    public Boolean checkCartStatus(int pid, int uid) {
        return shoppingCartMapper.getCartStatus(pid, uid) != null;
    }

    @Override
    public Boolean addGoods(ShoppingCart shoppingCart) {
        return shoppingCartMapper.addGoods(shoppingCart);
    }

    @Override
    public Boolean deleteGoods(int id) {
        return shoppingCartMapper.deleteGoods(id);
    }

    @Override
    public Boolean deleteGoodsList(List<Integer> ids) {
        return ids.stream().allMatch(this::deleteGoods);
    }

    @Override
    public Boolean alterGoodNumber(int id, int count) {
        return shoppingCartMapper.alterGoodsNumber(id, count);
    }

}
