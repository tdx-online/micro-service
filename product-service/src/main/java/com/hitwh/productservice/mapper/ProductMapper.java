package com.hitwh.productservice.mapper;



import com.hitwh.productservice.entity.Product;
import com.hitwh.productservice.entity.ProductDetails;
import com.hitwh.productservice.entity.ProductImage;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductMapper {
    /**
     * 根据商品类型id获取商品
     *
     * @param categoryId 商品类型id
     * @return 商品列表
     */
    @Select("select * from product where cid = #{categoryId} limit 30")
    List<ProductDetails> homePageGetProductsByCategoryId(int categoryId);

    /**
     * 新增商品
     *
     * @param product 商品对象
     * @return 是否添加成功
     */
    @Insert("insert into product(name, sub_title, original_price, promote_price, stock, cid, create_date) " +
            "values(#{name}, #{subTitle}, #{originalPrice}, #{promotePrice}, #{stock}, #{cid}, #{createDate})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    boolean addProduct(Product product);

    /**
     * 根据商品id获取商品图片
     *
     * @param productId 商品id
     * @return 图片对象
     */
    @Select("select * from product_image where pid = #{productId} and type = 'type_single' limit 1")
    ProductImage getOneImageByProductId(Integer productId);

    /**
     * 根据商品id获取商品图片列表
     *
     * @param productId 商品id
     * @return 图片列表
     */
    @Select("select * from product_image where pid = #{productId}")
    List<ProductImage> getImagesByProductId(Integer productId);

    /**
     * 根据商品id删除商品
     *
     * @param id 商品id
     * @return 是否删除成功
     */
    @Delete("delete from product where id = #{id}")
    boolean delete(Integer id);

    /**
     * 根据商品id获取商品详细信息
     *
     * @param id 商品id
     * @return 商品详细信息对象
     */
    @Select("select * from product where id = #{id}")
    ProductDetails queryForDetailsById(Integer id);

    /**
     * 根据商品名称模糊查询商品信息
     *
     * @param name 商品名称
     * @return 商品列表
     */
    @Select("SELECT * FROM product WHERE name LIKE CONCAT('%', #{name}, '%')")
    List<ProductDetails> queryByName(@Param("name") String name);


    /**
     * 根据商品id获取商品信息
     *
     * @param id 商品id
     * @return 商品对象
     */
    @Select("select * from product where id = #{id}")
    Product queryById(Integer id);

    /**
     * 根据商品id获取一个商品图片
     *
     * @param productId 商品id
     * @return 图片对象
     */
    @Select("select * from product_image where pid = #{productId} and type = 'type_single' limit 1")
    ProductImage getImageByProductId(Integer productId);

    /**
     * 根据商品id修改商品信息
     *
     * @param product 商品对象
     * @return 是否修改成功
     */
    @Update("update product set name=#{name}, sub_title=#{subTitle}, original_price=#{originalPrice}, " +
            "promote_price=#{promotePrice}, stock=#{stock}, cid=#{cid}, create_date=#{createDate} where id=#{id}")
    boolean updateProduct(Product product);

    /**
     * 根据商品id给商品添加图片
     *
     * @param productId 商品id
     * @param type      图片类型
     * @param url       图片url
     * @param middle    图片middle
     * @param small     图片small
     * @return 是否添加成功
     */
    @Insert("insert into product_image(pid, type, url_path, single_middle, single_small) " +
            "values(#{productId}, #{type}, #{url}, #{middle}, #{small})")
    boolean addImageByProductId(@Param("productId") int productId, @Param("type") String type,
                                @Param("url") String url, @Param("middle") String middle,
                                @Param("small") String small);

    /**
     * 根据商品id获取商品数量
     * @param id
     * @return
     */
    @Select("select stock from product where id = #{id}")
    Integer getStockById(Integer id);

    /**
     * 根据商品id修改商品数量
     * @param id
     * @param stock
     * @return
     */
    @Update("update product set stock = #{stock} where id = #{id}")
    boolean updateStockById(Integer id, Integer stock);
}
