package com.hitwh.userservice.mapper;


import com.hitwh.userservice.entity.ShoppingCart;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {
    /**
     * 根据用户ID返回当前用户购物车内容
     */
    @Select("SELECT * FROM shopping_cart WHERE uid = #{userId} AND status = 1")
    List<ShoppingCart> queryByUserId(@Param("userId") Integer userId);

    /**
     * 添加商品到购物车
     */
    @Insert("INSERT INTO shopping_cart(pid, count, uid, status) VALUES(#{pid}, #{count}, #{uid}, #{status})")
    Boolean addGoods(ShoppingCart shoppingCart);

    /**
     * 从购物车删除商品
     */
    @Insert("UPDATE shopping_cart SET status = 0 WHERE id = #{id} and status = 1")
    Boolean deleteGoods(int id);

    /**
     * 更改购物车中商品的数量
     */
    @Update("UPDATE shopping_cart SET count = #{count} WHERE id= #{id} AND status = 1")
    Boolean alterGoodsNumber(int id, int count);

    /**
     * 根据用户ID查询购物车商品数量
     *
     * @param id 用户ID
     * @return integer 商品数量
     */
    @Select("SELECT COUNT(*) FROM shopping_cart WHERE uid = #{id} AND status = 1")
    Integer getCount(int id);

    /**
     * 检查商品是否被加入到购物车中
     */
    @Select("SELECT id FROM shopping_cart WHERE uid = #{uid} AND pid = #{pid} AND status = 1")
    Integer getCartStatus(int pid, int uid);
}
