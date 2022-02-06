package com.ivanzap.marvel.web;

import com.ivanzap.marvel.model.Image;
import com.ivanzap.marvel.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImageController {
    private static final Logger log = LoggerFactory.getLogger(ImageController.class);

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/uploadImage")
    public Image uploadImage(@RequestParam("image") MultipartFile image) {
        log.info("uploadImage {}", image);
        return imageService.uploadImage(image);
    }

    @GetMapping("show/{image_id}")
    public Resource showImage(@PathVariable("image_id") Integer imageId) {
        log.info("show {}", imageId);
        return imageService.showImage(imageId);
    }
}
