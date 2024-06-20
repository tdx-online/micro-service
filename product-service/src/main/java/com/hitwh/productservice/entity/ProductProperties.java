package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductProperties {
    private int id;
    private int pid;
    private Integer ptid;
    private String name;
    private String value;
}
