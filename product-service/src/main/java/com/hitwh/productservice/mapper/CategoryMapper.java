package com.hitwh.productservice.mapper;



import com.hitwh.productservice.entity.Category;
import com.hitwh.productservice.entity.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {
    /**
     * 添加商品种类
     *
     * @param category 商品种类实例
     */
    @Insert("insert into category(name) values(#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addCategory(Category category);

    /**
     * 根据ID获取分类信息
     *
     * @param id 分类ID
     * @return 分类信息
     */
    @Select("select * from category where id = #{id}")
    Category getCategoryById(@Param("id") int id);

    /**
     * 根据ID删除分类
     *
     * @param id 分类ID
     * @return 删除成功返回true，否则返回false
     */
    @Delete("delete from category where id = #{id}")
    boolean deleteCategoryById(@Param("id") int id);

    /**
     * 获取所有分类信息
     *
     * @return 所有分类信息列表
     */
    @Select("select * from category")
    List<Category> listAllCategories();

    /**
     * 删除分类
     *
     * @param id 分类ID
     * @return 删除成功返回true，否则返回false
     */
    @Delete("delete from category where id = #{id}")
    boolean deleteCategory(@Param("id") int id);

    /**
     * 根据分类ID获取商品列表
     *
     * @param cid 分类ID
     * @return 商品列表
     */
    @Select("select * from product where cid  = #{cid}")
    List<Product> getPropertyByCid(@Param("cid") int cid);

    /**
     * 根据分类ID添加图片URL
     *
     * @param id  分类ID
     * @param url 图片URL
     * @return 添加成功返回true，否则返回false
     */
    @Update("update category set url_path = #{url} where id = #{id}")
    boolean addImageByCategoryId(@Param("id") Integer id, @Param("url") String url);
}