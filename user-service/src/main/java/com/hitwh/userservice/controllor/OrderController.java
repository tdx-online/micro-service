package com.hitwh.userservice.controllor;

import com.hitwh.userservice.entity.Order;
import com.hitwh.userservice.entity.ResultInfo;
import com.hitwh.userservice.entity.User;
import com.hitwh.userservice.mapper.ProductMapper;
import com.hitwh.userservice.service.OrderService;
import com.hitwh.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@CrossOrigin
@RestController
@RequestMapping("/order")
//@SessionAttributes("user")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    private final ProductMapper productMapper;

    @Autowired
    public OrderController(OrderService orderService, UserService userService, ProductMapper productMapper) {
        this.orderService = orderService;
        this.userService = userService;
        this.productMapper = productMapper;
    }

    @GetMapping("/getAllOrders")
    public ResultInfo getAllOrders(@RequestHeader(value = "Authorization", required = false) String token) {
        ResultInfo info = new ResultInfo();
        User user = token != null ? userService.getUserByToken(token) : null;
        if (user == null) {
            info.setFlag(false);
            info.setErrorMsg("用户未登录");
            return info;
        }
        List<Order> orders = orderService.getAllOrders();
        if (orders == null) {
            info.setFlag(false);
            info.setErrorMsg("获取订单失败");
        } else {
            info.setFlag(true);
            info.setData(orders);
        }
        return info;
    }

    /**
     * 根据用户ID获取订单
     *
     * @param userId 用户ID
     * @return 订单列表
     * @throws IOException IO异常
     */
    @GetMapping("/getOrdersByUserId")
    public ResultInfo getOrdersByUserId(@RequestHeader(value = "Authorization", required = false) String token, @RequestParam("uid") Integer userId) throws IOException {
        ResultInfo info = new ResultInfo();
        User user = token != null ? userService.getUserByToken(token) : null;
        if (user == null || userId == null) {
            info.setFlag(false);
            info.setErrorMsg("用户未登录或用户ID无效");
            return info;
        }

        List<Order> orders = orderService.getOrdersByUserId(userId);
        if (null == orders) {
            info.setFlag(false);
            info.setErrorMsg("获取订单失败");
        } else {
            info.setFlag(true);
            for (Order order : orders) {
                order.setOrderItems(orderService.getOrderItemsByOrderId(order.getId()));
            }
            info.setData(orders);
        }
        return info;
    }

    /**
     * 更新订单状态
     *
     * @param order 订单
     * @return 更新结果
     * @throws IOException IO异常
     */
    @PostMapping("/updateStatus")
    public ResultInfo updateStatus(@RequestHeader(value = "Authorization", required = false) String token, @RequestBody Order order) throws IOException {
        ResultInfo info = new ResultInfo();
        User user = token != null ? userService.getUserByToken(token) : null;
        if (user == null) {
            info.setFlag(false);
            info.setErrorMsg("用户未登录");
            return info;
        }
        boolean flag = orderService.updateStatus(order.getId(), order.getStatus());
        if (flag) {
            info.setFlag(true);
        } else {
            info.setFlag(false);
            info.setErrorMsg("更新订单失败");
        }
        return info;
    }

    /**
     * 删除订单
     *
     * @param oid  订单ID
     * @return 删除结果
     * @throws IOException IO异常
     */
    @PostMapping("/deleteOrder")
    public ResultInfo deleteOrder(@RequestHeader(value = "Authorization", required = false) String token, @RequestBody Integer oid) throws IOException {
        ResultInfo info = new ResultInfo();
        User user = token != null ? userService.getUserByToken(token) : null;
        if (user == null) {
            info.setFlag(false);
            info.setErrorMsg("用户未登录");
            return info;
        }
        boolean flag = orderService.deleteOrder(oid);
        if (flag) {
            info.setFlag(true);
        } else {
            info.setFlag(false);
            info.setErrorMsg("删除订单失败");
        }
        return info;
    }

    /**
     * 创建订单
     *
     * @param order 订单
     * @return 创建结果
     * @throws IOException IO异常
     */
    @PostMapping("/createOrder")
    public ResultInfo createOrder(@RequestHeader(value = "Authorization", required = false) String token, @RequestBody Order order) throws IOException {
        ResultInfo info = new ResultInfo();
        User user = token != null ? userService.getUserByToken(token) : null;
        if (user == null) {
            info.setFlag(false);
            info.setErrorMsg("用户未登录");
            return info;
        }


        String orderCode = generateOrderCode();
        String createDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        int status = 0;

        order.setOrderCode(orderCode);
        order.setCreateDate(createDate);
        order.setStatus(status);

        boolean flag = orderService.createOrder(order);
        if (flag) {
            info.setFlag(true);
        } else {
            info.setFlag(false);
            info.setErrorMsg("创建订单失败，库存不足");
        }
        return info;
    }

    /**
     * 生成订单号
     *
     * @return 订单号
     */
    private static String generateOrderCode() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String datePrefix = dtf.format(LocalDateTime.now());

        Random rand = new Random();
        int randomSuffix = 1000 + rand.nextInt(9000);

        return datePrefix + randomSuffix;
    }
}
