package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductImage {
    private int id;
    private int pid;
    private String type;
    private String urlPath;
    private String singleMiddle;
    private String singleSmall;
}
