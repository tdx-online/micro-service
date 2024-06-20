package com.hitwh.productservice.controller;


import entity.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.CategoryService;

@CrossOrigin
@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * 根据商品分类ID删除分类
     *
     * @param categoryId 商品分类ID
     * @return 删除结果
     */
    @DeleteMapping("/{categoryId}")
    public ResultInfo delete(@PathVariable("categoryId") Integer categoryId) {
        ResultInfo info = new ResultInfo();
        if (categoryId == null || categoryId <= 0) {
            info.setFlag(false);
            info.setErrorMsg("删除商品分类失败");
        } else {
            if (categoryService.deleteCategory(categoryId)) {
                info.setFlag(true);
            } else {
                info.setFlag(false);
                info.setErrorMsg("删除商品分类失败");
            }
        }
        return info;
    }

    /**
     * 获取首页分类
     *
     * @return 首页分类
     */
    @GetMapping("/homePageCategory")
    public ResultInfo homePageCategory() {
        ResultInfo info = new ResultInfo();
        info.setData(categoryService.categoryAndProduct());
        if (info.getData() != null) {
            info.setFlag(true);
        } else {
            info.setFlag(false);
            info.setErrorMsg("获取首页分类失败");
        }
        return info;
    }

    /**
     * 获取所有分类
     *
     * @return 分类
     */
    @GetMapping("/listAllCategories")
    public ResultInfo listAllCategories() {
        ResultInfo info = new ResultInfo();
        info.setData(categoryService.listAllCategories());
        if (info.getData() != null) {
            info.setFlag(true);
        } else {
            info.setFlag(false);
            info.setErrorMsg("获取所有分类失败");
        }
        return info;
    }

    /**
     * 添加新的商品分类
     *
     * @param categoryName 商品分类名称
     * @return 添加结果
     */
    @PostMapping("/add")
    public ResultInfo add(String categoryName) {
        ResultInfo info = new ResultInfo();
        if (categoryName == null || categoryName.isEmpty()) {
            info.setFlag(false);
            info.setErrorMsg("添加商品分类失败");
        } else {
            info.setData(categoryService.addCategory(categoryName));
            if (info.getData() != null && (Integer) info.getData() != 0) {
                info.setFlag(true);
            } else {
                info.setFlag(false);
                info.setErrorMsg((Integer) info.getData() == 0 ? "该分类已存在" : "添加商品分类失败");
            }
        }
//        System.out.println(info.getData());
        return info;
    }

    /**
     * 根据分类ID获取商品内容
     *
     * @param cid 分类ID
     * @return 商品内容
     */
    @GetMapping("/searchCategoryProperty/{cid}")
    public ResultInfo searchCategoryProperty(@PathVariable("cid") Integer cid) {
        ResultInfo info = new ResultInfo();
        if (cid == null || cid <= 0) {
            info.setFlag(false);
            info.setErrorMsg("根据分类cid获取商品内容失败");
        } else {
            info.setData(categoryService.cidCategoryProperty(cid));
            if (info.getData() != null) {
                info.setFlag(true);
            } else {
                info.setFlag(false);
                info.setErrorMsg("根据分类cid获取商品内容失败");
            }
        }
        return info;
    }
}
