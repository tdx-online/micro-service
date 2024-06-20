package com.hitwh.userservice.mapper;


import com.hitwh.userservice.entity.Order;
import com.hitwh.userservice.entity.OrderItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {
    /**
     * 获取所有订单数据
     * @return
     */
    @Select("SELECT o.id, o.order_code, o.address, o.post, o.receiver, o.receiver_tel, o.user_message, " +
            "o.create_date, o.pay_date, o.delivery_date, o.confirm_date, o.uid, o.status, " +
            "SUM(oi.count) AS productCount, SUM(p.promote_price * oi.count) AS totalPrice " +
            "FROM tdx_online.`order` AS o " +
            "LEFT JOIN tdx_online.order_item AS oi ON o.id = oi.oid " +
            "LEFT JOIN tdx_online.product AS p ON oi.pid = p.id " +
            "GROUP BY o.id")
    List<Order> getAllOrders();

    /**
     * 获取某个用户的所有订单
     * @param userId
     * @return
     */
    @Select("SELECT o.id, o.order_code, o.receiver_tel, o.user_message, o.create_date, " +
            "o.pay_date, o.delivery_date, o.confirm_date, o.uid, o.status, " +
            "SUM(oi.count) AS productCount, SUM(p.promote_price * oi.count) AS total_price " +
            "FROM tdx_online.`order` AS o " +
            "LEFT JOIN tdx_online.order_item AS oi ON o.id = oi.oid " +
            "LEFT JOIN tdx_online.product AS p ON oi.pid = p.id " +
            "WHERE o.uid = #{userId} " +
            "GROUP BY o.id")
    List<Order> getOrdersByUserId(@Param("userId") Integer userId);

    /**
     * 根据订单id获取订单商品
     * @param orderId
     * @return
     */
    @Select("SELECT oi.oid, oi.id, oi.pid, p.name, p.original_price, p.promote_price, oi.count " +
            "FROM tdx_online.order_item AS oi " +
            "LEFT JOIN tdx_online.product AS p ON oi.pid = p.id " +
            "WHERE oi.oid = #{orderId}")
    List<OrderItem> getOrderItemsByOrderId(@Param("orderId") int orderId);

    /**
     * 更新某个订单的状态（0待付款、1待发货、2待收货、3待评价）
     * @param oid
     * @param status
     * @return
     */
    @Update("UPDATE tdx_online.`order` SET status = #{status} WHERE id = #{oid}")
    boolean updateStatus(@Param("oid") Integer oid, @Param("status") int status);

    /**
     * 删除某个订单
     * @param oid
     * @return
     */
    @Delete("DELETE FROM tdx_online.`order` WHERE id = #{oid}")
    boolean deleteOrder(@Param("oid") Integer oid);

    /**
     * 创建订单
     * @param order
     * @return
     */
    @Insert("INSERT INTO tdx_online.`order` (order_code, receiver_tel, user_message, create_date, uid, status) " +
            "VALUES (#{orderCode}, #{receiverTel}, #{userMessage}, #{createDate}, " +
            "#{uid}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    boolean createOrder(Order order);

    /**
     * 创建订单商品
     * @param oid
     * @param orderItems
     * @return
     */
    @Insert({
            "<script>",
            "INSERT INTO tdx_online.order_item (pid, oid, count) VALUES ",
            "<foreach collection='orderItems' item='item' separator=','>",
            "(#{item.pid}, #{oid}, #{item.count})",
            "</foreach>",
            "</script>"
    })
    boolean createOrderItems(@Param("oid") int oid, @Param("orderItems") List<OrderItem> orderItems);

}
