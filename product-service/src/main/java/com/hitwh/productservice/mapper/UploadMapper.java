package mapper;

import java.io.InputStream;

//@Repository
public interface UploadMapper {
    /**
     * 上传图片
     */
    String uploadImage(String imageName, InputStream input, String type);

    void destroy();
}
