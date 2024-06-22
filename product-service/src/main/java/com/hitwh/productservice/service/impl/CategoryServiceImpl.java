package com.hitwh.productservice.service.impl;


import com.hitwh.productservice.entity.Category;
import com.hitwh.productservice.entity.ProductDetails;
import com.hitwh.productservice.mapper.CategoryMapper;
import com.hitwh.productservice.mapper.ProductMapper;
import com.hitwh.productservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper;
    private final ProductMapper productMapper;

    @Autowired
    public CategoryServiceImpl(CategoryMapper categoryMapper, ProductMapper productMapper) {
        this.categoryMapper = categoryMapper;
        this.productMapper = productMapper;
    }

    @Override
    public Object categoryAndProduct() {
        List<Category> categories = categoryMapper.listAllCategories();
        List<Object> categoryAndProduct = new ArrayList<>();
        for (Category category : categories) {
            Map<String, Object> categoryMap = new HashMap<>();
            categoryMap.put("cid", category.getId());
            categoryMap.put("category_name", category.getName());
            categoryMap.put("category_image", category.getUrlPath());

            List<ProductDetails> products = productMapper.homePageGetProductsByCategoryId(category.getId());
            products.forEach(product -> product.setImages(productMapper.getImagesByProductId(product.getId())));
            categoryMap.put("products", products);

            categoryAndProduct.add(categoryMap);
        }
        return categoryAndProduct;
    }

    @Override
    public Object listAllCategories() {
        return categoryMapper.listAllCategories();
    }

    @Override
    public boolean deleteCategory(int i) {
        return categoryMapper.deleteCategory(i);
    }

    @Override
    public int addCategory(String categoryName) {
        Category category = new Category();
        category.setName(categoryName);
        categoryMapper.addCategory(category);
        return category.getId();
    }

    @Override
    public Object cidCategoryProperty(int i) {
//        System.out.println(i);
        return categoryMapper.getPropertyByCid(i);
    }
}
