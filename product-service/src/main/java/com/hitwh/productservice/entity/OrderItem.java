package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    private int id;
    private int pid;
    private int oid;
    private String name;
    private Double originalPrice;
    private Double promotePrice;
    private int count;
    private ProductImage productImage;
}
