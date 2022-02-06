package com.ivanzap.marvel.util;

import com.ivanzap.marvel.util.exception.ImageUploadException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class ImageUtils {

    public static boolean upload(MultipartFile image, String uploadPath) {
        if (image != null) {

            String imageName = image.getOriginalFilename();

            int dot = imageName.lastIndexOf('.');
            String fmt = "";
            if ((dot > -1) && (dot < (imageName.length() - 1))) {
                fmt = imageName.substring(dot + 1);
            }

            if ("png".equalsIgnoreCase(fmt) || "jpg".equalsIgnoreCase(fmt) || "gif".equalsIgnoreCase(fmt)) {
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

                try {
                    image.transferTo(new File(uploadPath + "/" + imageName));
                    return true;
                } catch (IllegalStateException | IOException e) {
                    e.printStackTrace();
                    throw new ImageUploadException("Image saving error");
                }
            } else {
                throw new ImageUploadException("The file has not been uploaded, the file format is not supported");
            }
        } else {
            throw new ImageUploadException("Error. The uploaded image is empty");
        }
    }
}
