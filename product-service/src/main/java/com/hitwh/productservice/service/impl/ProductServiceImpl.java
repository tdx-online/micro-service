package service.impl;


import entity.Product;
import entity.ProductDetails;
import entity.ProductImage;
import mapper.CategoryMapper;
import mapper.CommentMapper;
import mapper.ProductMapper;
import mapper.PropertyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.ProductService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;
    private final PropertyMapper propertyMapper;
    private final CommentMapper commentMapper;
    private final CategoryMapper categoryMapper;

    @Autowired
    public ProductServiceImpl(ProductMapper productMapper, PropertyMapper propertyMapper, CommentMapper commentMapper, CategoryMapper categoryMapper) {
        this.productMapper = productMapper;
        this.propertyMapper = propertyMapper;
        this.commentMapper = commentMapper;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public boolean addProduct(Product product) {
//        System.out.println("ProductServiceImpl--addProduct");
        product.setCreateDate(new Timestamp(System.currentTimeMillis()));
        return productMapper.addProduct(product);
    }

    @Override
    public boolean delete(int id) {
//        System.out.println("ProductServiceImpl--delete");
        return productMapper.delete(id);
    }

    @Override
    public Object details(int id) {
        ProductDetails product = productMapper.queryForDetailsById(id);
        if (product == null) {
            return null;
        }
        product.setImages(productMapper.getImagesByProductId(id));
        product.setProperties(propertyMapper.getPropertiesByProductId(id));
        product.setComments(commentMapper.getCommentsByProductId(id));
        product.setCategory(categoryMapper.getCategoryById(product.getCid()));
        return product;
    }

    @Override
    public List<ProductDetails> search(String name) {
        if (name.equals("NULL"))
            name = "";
        List<ProductDetails> products = productMapper.queryByName(name);
        if (products == null) {
            return null;
        }
        products.forEach(product -> {
            List<ProductImage> list = new ArrayList<>();
            list.add(productMapper.getOneImageByProductId(product.getId()));
            product.setImages(list);
        });
        return products;
    }

    @Override
    public boolean changeProduct(Product product) {
//        System.out.println("changeProduct");
        product.setCreateDate(new Timestamp(System.currentTimeMillis()));
        return productMapper.updateProduct(product);
    }
}
