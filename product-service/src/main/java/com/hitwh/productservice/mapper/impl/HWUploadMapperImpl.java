package com.hitwh.productservice.mapper.impl;

import com.hitwh.productservice.mapper.UploadMapper;
import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.PutObjectResult;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
@Primary
public class HWUploadMapperImpl implements UploadMapper {
    private final String ACCESS_KEY = System.getenv("HW_OBS_AK");
    private final String SECRET_KEY = System.getenv("HW_OBS_SAK");
    private final String endPoint = "https://obs.cn-east-5.myhuaweicloud.com";
    private final ObsClient obsClient = new ObsClient(ACCESS_KEY, SECRET_KEY, endPoint);

    @Override
    public String uploadImage(String imageName, InputStream input, String type) {
        switch (type) {
            case "category" -> imageName = "category/" + imageName;
            case "type_single" -> imageName = "productSingle/" + imageName;
            case "type_detail" -> imageName = "productDetail/" + imageName;
            case "type_single_middle" -> imageName = "productSingleMiddle/" + imageName;
            case "type_single_small" -> imageName = "productSingleSmall/" + imageName;
            default -> {
                return null;
            }
        }
        String bucketName = "tdx-online-store";

        try {
            PutObjectResult putObjectResult = obsClient.putObject(bucketName, imageName, input);
            return putObjectResult.getObjectUrl();
        } catch (ObsException e) {
            System.out.println("putObject failed");
            // 请求失败,打印http状态码
            System.out.println("HTTP Code:" + e.getResponseCode());
            // 请求失败,打印服务端错误码
            System.out.println("Error Code:" + e.getErrorCode());
            // 请求失败,打印详细错误信息
            System.out.println("Error Message:" + e.getErrorMessage());
            // 请求失败,打印请求id
            System.out.println("Request ID:" + e.getErrorRequestId());
            System.out.println("Host ID:" + e.getErrorHostId());
//            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("putObject failed");
            // 其他异常信息打印
//            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void destroy() {
        try {
            obsClient.close();
        } catch (IOException ignore) {
        }
    }
}
