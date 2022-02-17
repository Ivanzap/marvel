package com.ivanzap.marvel.to;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ComicTo extends BaseTo {

    @NotBlank(message = "Title should not be blank")
    @Size(min = 1, max = 255, message = "Title should be between 1 and 255 characters")
    private String title;

    @NotBlank(message = "Description should not be blank")
    @Size(min = 1, max = 255, message = "Description should be between 1 and 255 characters")
    private String description;

    private MultipartFile image;

    public ComicTo() {
    }

    public ComicTo(String title, String description, MultipartFile image) {
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
