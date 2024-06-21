package com.hitwh.productservice.controller;



import com.hitwh.productservice.entity.ResultInfo;
import com.hitwh.productservice.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.io.InputStream;

@CrossOrigin
@RestController
@RequestMapping("/upload")
public class UploadController {
    private final UploadService uploadService;

    @Autowired
    public UploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    /**
     * 上传商品图片
     *
     * @param filename 文件名
     * @param file     文件
     * @param type     类型
     * @param pid      商品id
     * @return 上传结果
     */
    @PostMapping("/product")
    @ResponseBody
    public ResultInfo product(@RequestParam("filename") String filename,
                              @RequestParam("file") MultipartFile file,
                              @RequestParam("type") String type,
                              @RequestParam("pid") int pid) {
//        System.out.println("product");
        ResultInfo info = new ResultInfo();
        try {
            InputStream inputStream = file.getInputStream();
            info.setFlag(uploadService.uploadImage(filename, inputStream, type, pid));
        } catch (NumberFormatException e) {
            info.setFlag(false);
            info.setErrorMsg("请输入正确的商品id");
        } catch (IOException e) {
            info.setFlag(false);
            info.setErrorMsg("上传失败");
//            e.printStackTrace();
        }
        return info;
    }

    /**
     * 上传商品分类图片
     *
     * @param filename 文件名
     * @param file     文件
     * @param id       商品分类id
     * @return 上传结果
     */
    @PostMapping("/category")
    @ResponseBody
    public ResultInfo category(@RequestParam("filename") String filename,
                               @RequestParam("file") MultipartFile file,
                               @RequestParam("id") int id) {
//        System.out.println("category");
        ResultInfo info = new ResultInfo();
        try {
            InputStream inputStream = file.getInputStream();
            info.setFlag(uploadService.uploadImage(filename, inputStream, "category", id));
        } catch (NumberFormatException e) {
            info.setFlag(false);
            info.setErrorMsg("请输入正确的商品分类id");
        } catch (IOException e) {
            info.setFlag(false);
            info.setErrorMsg("上传失败");
//            e.printStackTrace();
        }
        return info;
    }
}