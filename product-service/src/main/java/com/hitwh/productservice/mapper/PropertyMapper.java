package mapper;


import entity.ProductProperties;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PropertyMapper {
    /**
     * 根据商品分类id获取商品属性
     */
    @Select("SELECT * FROM property WHERE cid = #{categoryId}")
    List<Property> getPropertyByCategoryId(@Param("categoryId") Integer categoryId);

    /**
     * 根据商品id获取商品的所有属性
     */
    @Select("SELECT COALESCE(id, '') AS id, COALESCE(pid, '') AS pid, COALESCE(ptid, '') AS ptid, " +
            "COALESCE(name, '') AS name, COALESCE(value, '') AS value " +
            "FROM product_properties_view WHERE pid = #{productId}")
    List<ProductProperties> getPropertiesByProductId(@Param("productId") int productId);

    /**
     * 添加商品属性
     */
    @Insert("INSERT INTO property(cid, name) VALUES (#{cid}, #{name})")
    boolean addProperty(@Param("cid") int cid, @Param("name") String name);

    /**
     * 修改属性值
     */
    @Update("UPDATE property_value SET value = #{value} WHERE id = #{id}")
    boolean changeProperty(@Param("id") int id, @Param("value") String value);

    /**
     * 添加商品属性值
     */
    @Insert("INSERT INTO property_value(pid, ptid, value) VALUES (#{pid}, #{ptid}, #{value})")
    boolean addProductProperty(@Param("pid") int pid, @Param("ptid") int ptid, @Param("value") String value);
}