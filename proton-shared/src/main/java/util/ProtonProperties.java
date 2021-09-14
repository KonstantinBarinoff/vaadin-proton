package util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("proton")
public class ProtonProperties {

    private String imagePath;
    private String fileStoragePath;
    private Integer fileStorageWidth = 10;

    public Integer getFileStorageWidth() {
        return fileStorageWidth;
    }

    public void setFileStorageWidth(Integer fileStorageWidth) {
        this.fileStorageWidth = fileStorageWidth;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getFileStoragePath() {
        return fileStoragePath;
    }

    public void setFileStoragePath(String fileStoragePath) {
        this.fileStoragePath = fileStoragePath;
    }


}
