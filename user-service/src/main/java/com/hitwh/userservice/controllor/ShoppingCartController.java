package com.hitwh.userservice.controllor;


import com.hitwh.userservice.entity.ResultInfo;
import com.hitwh.userservice.entity.ShoppingCart;
import com.hitwh.userservice.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    /**
     * 获取购物车信息
     * @param userId 用户id
     * @return 购物车信息
     */
    @GetMapping("/show")
    public ResultInfo show(@RequestParam("id") int userId) {
        ResultInfo info = new ResultInfo();
        try {
            info.setData(shoppingCartService.getShoppingCartByUserId(userId));
            info.setFlag(true);
        } catch (NumberFormatException e) {
            info.setFlag(false);
            info.setErrorMsg("请给出正确数据类型!");
        } catch (Exception e) {
            info.setFlag(false);
            info.setErrorMsg("获取失败!");
        }
        return info;
    }

    /**
     * 添加商品到购物车
     * @param shoppingCart 商品信息
     * @return 是否添加成功
     */
    @PostMapping("/addGoods")
    public ResultInfo addGoods(@RequestBody ShoppingCart shoppingCart) {
        ResultInfo resultInfo = new ResultInfo();
        try {
            shoppingCart.setStatus(1);
            Boolean addFlag = shoppingCartService.addGoods(shoppingCart);
            resultInfo.setFlag(addFlag);
            resultInfo.setErrorMsg(addFlag ? null : "fail to insert!!!(db)");
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("fail to add goods!");
        }
        return resultInfo;
    }

    /**
     * 删除购物车中商品
     * @param id 商品id
     * @return 是否删除成功
     */
    @PostMapping("/deleteGoods")
    public ResultInfo deleteGoods(@RequestParam("id") Integer id) {
        ResultInfo info = new ResultInfo();
        try {
            boolean delStatus = shoppingCartService.deleteGoods(id);
            info.setFlag(delStatus);
            info.setErrorMsg(delStatus ? null : "fail to del!!!(db)");
        } catch (Exception e) {
            info.setFlag(false);
            info.setErrorMsg("fail to del!!!");
        }
        return info;
    }

    /**
     * 删除购物车中商品
     * @param dataMap 商品id列表
     * @return 是否删除成功
     */
    @PostMapping("/deleteGoodsByList")
    public ResultInfo deleteGoodsByList(@RequestBody Map<String, List<Integer>> dataMap) {
        ResultInfo info = new ResultInfo();
        try {
            boolean delFlag = shoppingCartService.deleteGoodsList(dataMap.get("id"));
            info.setFlag(delFlag);
            info.setErrorMsg(delFlag ? null : "fail to delete list!!!(db)");
        } catch (Exception e) {
            info.setFlag(false);
            info.setErrorMsg("fail to delete list!!!");
        }
        return info;
    }

    /**
     * 修改购物车中商品数量
     * @param shoppingCartId 购物车id
     * @param count 商品数量
     * @return 是否修改成功
     */
    @PostMapping("/alterGoodsNumber")
    public ResultInfo alterGoodsNumber(@RequestParam("id") int shoppingCartId, @RequestParam("count") int count) {
        ResultInfo info = new ResultInfo();
        try {
            boolean alterFlag = shoppingCartService.alterGoodNumber(shoppingCartId, count);
            info.setFlag(alterFlag);
            info.setErrorMsg(alterFlag ? null : "fail to alter!!!(db)");
        } catch (Exception e) {
            info.setFlag(false);
            info.setErrorMsg("fail to alter!!!");
        }
        return info;
    }

    /**
     * 获取购物车商品数量
     * @param userId 用户id
     * @return 商品数量
     */
    @GetMapping("/getCount")
    public ResultInfo getCount(@RequestParam("id") int userId) {
        ResultInfo info = new ResultInfo();
        try {
            info.setData(shoppingCartService.getShoppingCartCount(userId));
            info.setFlag(true);
        } catch (NumberFormatException e) {
            info.setFlag(false);
            info.setErrorMsg("请给出正确数据类型!");
        } catch (Exception e) {
            info.setFlag(false);
            info.setErrorMsg("获取失败!");
        }
        return info;
    }

    /**
     * 检查购物车中是否存在该商品
     * @param uid 用户id
     * @param pid 商品id
     * @return 是否存在
     */
    @GetMapping("/checkCartStatus")
    public ResultInfo checkCartStatus(@RequestParam("uid") Integer uid, @RequestParam("pid") Integer pid) {
        ResultInfo info = new ResultInfo();
        try {
            boolean cartFlag = shoppingCartService.checkCartStatus(pid, uid);
            info.setFlag(true);
            info.setData(cartFlag);
        } catch (Exception e) {
            e.printStackTrace();
            info.setFlag(false);
            info.setErrorMsg("fail to check!!!");
        }
        return info;
    }
}
