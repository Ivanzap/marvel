package com.ivanzap.marvel.to;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CharacterTo extends BaseTo {

    @NotBlank(message = "Name should not be blank")
    @Size(min = 1, max = 255, message = "Name should be between 1 and 255 characters")
    private String name;

    @NotBlank(message = "Description should not be blank")
    @Size(min = 1, max = 255, message = "Description should be between 1 and 255 characters")
    private String description;

    private MultipartFile image;

    public CharacterTo() {
    }

    public CharacterTo(String name, String description, MultipartFile image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
