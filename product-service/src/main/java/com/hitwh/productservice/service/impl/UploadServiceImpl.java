package service.impl;


import mapper.CategoryMapper;
import mapper.ProductMapper;
import mapper.UploadMapper;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.UploadService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class UploadServiceImpl implements UploadService {
    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;
    //    @Qualifier("aliUploadMapperImpl")
    private final UploadMapper uploadMapper;

    @Autowired
    public UploadServiceImpl(ProductMapper productMapper, CategoryMapper categoryMapper, UploadMapper uploadMapper) {
        this.productMapper = productMapper;
        this.categoryMapper = categoryMapper;
        this.uploadMapper = uploadMapper;
    }

    @Override
    public boolean uploadImage(String imageName, InputStream inputStream, String type, Integer id) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            IOUtils.copy(inputStream, outputStream);
            imageName = generateUniqueFileName(imageName);
            String url = uploadMapper.uploadImage(imageName, new ByteArrayInputStream(outputStream.toByteArray()), type);
            if (type.equals("category")) {
                if (url == null)
                    return false;
                return categoryMapper.addImageByCategoryId(id, url);
            } else {
                String middle = "";
                String small = "";
                if (type.equals("type_single")) {
                    middle = uploadMapper.uploadImage(imageName,
                            resizeImage(new ByteArrayInputStream(outputStream.toByteArray()),
                                    217), "type_single_middle");
                    small = uploadMapper.uploadImage(imageName,
                            resizeImage(new ByteArrayInputStream(outputStream.toByteArray()),
                                    56), "type_single_small");
                    System.out.println(middle);
                    System.out.println(small);
                    if (middle == null || small == null)
                        return false;
                }
                return productMapper.addImageByProductId(id, type, url, middle, small);
            }
        } catch (Exception e) {
//            e.printStackTrace();
            return false;
        }
    }

    private InputStream resizeImage(InputStream inputStream, int newWidth) throws IOException {
        BufferedImage inputImage = ImageIO.read(inputStream);

        int currentWidth = inputImage.getWidth();
        int currentHeight = inputImage.getHeight();
        int newHeight = (int) Math.round((double) currentHeight / currentWidth * newWidth);

        //create new image
        Image newImage = inputImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        BufferedImage outputImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

        //draw new image
        Graphics2D graphics2D = outputImage.createGraphics();
        graphics2D.drawImage(newImage, 0, 0, null);
        graphics2D.dispose();

        //convert buffered image to input stream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(outputImage, "jpg", outputStream);
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    private String generateUniqueFileName(String originalFileName) {
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        return UUID.randomUUID() + extension;
    }
}
