package com.ivanzap.marvel.to;

import com.ivanzap.marvel.model.Comic;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

public class CharacterTo extends BaseTo {

    @NotBlank
    @Size(min = 1, max = 255)
    private String name;

    @NotBlank
    @Size(min = 1, max = 255)
    private String description;

    private List<Comic> comics;

    private MultipartFile image;

    public CharacterTo() {
    }

    public CharacterTo(String name, String description, List<Comic> comics, MultipartFile image) {
        this.name = name;
        this.description = description;
        this.comics = comics;
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

    public List<Comic> getComics() {
        return comics;
    }

    public void setComics(List<Comic> comics) {
        this.comics = comics;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
