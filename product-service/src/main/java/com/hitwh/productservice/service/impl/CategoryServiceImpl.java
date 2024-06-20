package service.impl;


import entity.Category;
import entity.ProductDetails;
import mapper.CategoryMapper;
import mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.CategoryService;

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
//        categories.forEach(System.out::println);
        for (Category category : categories) {
            Map<String, Object> categoryMap = new HashMap<>();
            categoryMap.put("cid", category.getId());
            categoryMap.put("category_name", category.getName());
            categoryMap.put("category_image", category.getUrlPath());

            List<ProductDetails> products = productMapper.homePageGetProductsByCategoryId(category.getId());
//            products.forEach(System.out::println);
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
