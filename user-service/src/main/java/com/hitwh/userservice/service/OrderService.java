package com.hitwh.userservice.service;

import com.hitwh.userservice.entity.Order;
import com.hitwh.userservice.entity.OrderItem;

import java.io.IOException;
import java.util.List;

public interface OrderService {
    /**
     * 获取所有订单数据
     *
     * @return 所有订单数据
     */
    List<Order> getAllOrders();

    /**
     * 获取某个用户的所有订单
     *
     * @param userId 用户id
     * @return 某个用户的所有订单
     */
    List<Order> getOrdersByUserId(Integer userId) throws IOException;

    /**
     * 更新某个订单的状态（待付款、待发货、待收货、待评价）
     */
    boolean updateStatus(Integer oid, int status) throws IOException;

    /**
     * 删除某个订单
     *
     * @param oid 订单id
     * @return 是否删除成功
     * @throws IOException IO异常
     */
    boolean deleteOrder(Integer oid) throws IOException;

    /**
     * 创建订单
     *
     * @param order 订单
     * @return 是否创建成功
     * @throws IOException IO异常
     */
    boolean createOrder(Order order) throws IOException;

    List<OrderItem> getOrderItemsByOrderId(Integer orderId) throws IOException;
}
