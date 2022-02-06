package com.ivanzap.marvel.service;

import com.ivanzap.marvel.model.Image;
import com.ivanzap.marvel.repository.ImageRepository;
import com.ivanzap.marvel.util.ImageUtils;
import com.ivanzap.marvel.util.exception.ImageNotFoundException;
import com.ivanzap.marvel.util.exception.ImageUploadException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;

@Service
public class ImageService {
    private final String uploadPath;
    private final ResourceLoader resourceLoader;
    private final ImageRepository imageRepository;

    public ImageService(ResourceLoader resourceLoader, ImageRepository imageRepository) {
        this.resourceLoader = resourceLoader;
        this.imageRepository = imageRepository;
        this.uploadPath = Paths.get("/temp/images/").toAbsolutePath().normalize().toString();
    }

    public Image uploadImage(MultipartFile image) {
        if (ImageUtils.upload(image, uploadPath)) {
            Image uploadImage = new Image();
            uploadImage.setPath(uploadPath + "/" + image.getOriginalFilename());
            return imageRepository.save(uploadImage);
        } else {
            throw new ImageUploadException("Ошибка загрузки изображения");
        }
    }

    public Resource showImage(Integer imageId) {
        String imagePath = imageRepository.findById(imageId).orElseThrow(() -> new ImageNotFoundException("" + imageId)).getPath();
        return resourceLoader.getResource("file:" + imagePath);
    }
}
