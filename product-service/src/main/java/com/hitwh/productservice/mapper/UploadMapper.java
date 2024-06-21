package com.hitwh.productservice.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.io.InputStream;

//@Repository
@Mapper
public interface UploadMapper {
    /**
     * 上传图片
     */
    String uploadImage(String imageName, InputStream input, String type);

    void destroy();
}
