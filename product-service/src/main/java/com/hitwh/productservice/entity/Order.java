package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private int id;
    private String orderCode;
    private String address;
    private int productCount;
    private Double totalPrice;
    private String post;
    private String receiver;
    private String receiverTel;
    private String userMessage;
    private String createDate;
    private String payDate;
    private String deliveryDate;
    private String confirmDate;
    private List<OrderItem> orderItems;
    private int uid;
    private int status;
}
