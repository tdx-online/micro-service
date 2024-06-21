package com.hitwh.productservice.controller;


import com.hitwh.productservice.entity.Product;
import com.hitwh.productservice.entity.ResultInfo;
import com.hitwh.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /*
     * @Description: 添加商品
     * @Param product:产品名称、产品价格、产品库存、产品小标题、产品优惠价格、产品类型id
     */
    @PostMapping("/add")
    public ResultInfo addProduct(@RequestBody Product product) {
        ResultInfo info = new ResultInfo();
        try {
            if (productService.addProduct(product)) {
                info.setFlag(true);
            } else {
                info.setFlag(false);
                info.setErrorMsg("添加失败");
            }
        } catch (Exception e) {
            info.setFlag(false);
            info.setErrorMsg("添加失败");
        }
        return info;
    }

    /*
     * @Description: 删除商品
     * @Param id:产品id
     */
    @DeleteMapping("/{id}")
    public ResultInfo deleteProduct(@PathVariable int id) {
        ResultInfo info = new ResultInfo();
        try {
            if (productService.delete(id)) {
                info.setFlag(true);
            } else {
                info.setFlag(false);
                info.setErrorMsg("删除失败");
            }
        } catch (Exception e) {
            info.setFlag(false);
            info.setErrorMsg("删除失败");
        }
        return info;
    }

    /*
     * @Description: 商品详情
     * @Param id:产品id
     */
    @GetMapping("/details/{id}")
    public ResultInfo getProductDetails(@PathVariable int id) {
        ResultInfo info = new ResultInfo();
        try {
            info.setData(productService.details(id));
            info.setFlag(true);
        } catch (Exception e) {
            info.setFlag(false);
            info.setErrorMsg("查询失败");
        }
        return info;
    }

    /*
     * @Description: 搜索商品
     * @Param name:产品名称
     */
    @GetMapping("/search/{name}")
    public ResultInfo searchProduct(@PathVariable String name) {
        ResultInfo info = new ResultInfo();
        try {
            info.setData(productService.search(name));
            info.setFlag(true);
        } catch (Exception e) {
            info.setFlag(false);
            info.setErrorMsg("查询失败");
        }
        return info;
    }

    /*
     * @Description: 修改商品
     * @Param product:产品名称、产品价格、产品库存、产品小标题、产品优惠价格、产品类型id
     */
    @PutMapping("/edit")
    public ResultInfo editProduct(@RequestBody Product product) {
        ResultInfo info = new ResultInfo();
        try {
            if (productService.changeProduct(product)) {
                info.setFlag(true);
            } else {
                info.setFlag(false);
                info.setErrorMsg("添加失败");
            }
        } catch (Exception e) {
            info.setFlag(false);
            info.setErrorMsg("添加失败");
        }
        return info;
    }
}
