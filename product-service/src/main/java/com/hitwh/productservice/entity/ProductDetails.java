package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetails extends Product {
    private List<ProductImage> images;
    private List<ProductProperties> properties;
    private List<Comment> comments;
    private Category category;
}
