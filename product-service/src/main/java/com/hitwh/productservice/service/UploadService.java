package com.hitwh.productservice.service;

import java.io.InputStream;

public interface UploadService {
    /**
     * 上传图片
     *
     * @param imageName   图片名
     * @param inputStream 图片输入流
     * @param type        图片类型
     * @param pid         商品id
     * @return 是否上传成功
     */
    boolean uploadImage(String imageName, InputStream inputStream, String type, Integer pid);
}