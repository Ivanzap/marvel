package com.ivanzap.marvel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

public class Comic extends AbstractBaseEntity {

    @NotBlank(message = "Title should not be blank")
    @Size(min = 1, max = 255, message = "Title should be between 1 and 255 characters")
    private String title;

    @NotBlank(message = "Description should not be blank")
    @Size(min = 1, max = 255, message = "Description should be between 1 and 255 characters")
    private String description;

    @JsonIgnore
    private List<Character> characters;

    public Comic(Integer id, String title, String description) {
        super(id);
        this.title = title;
        this.description = description;
    }

    public Comic() {
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

    public List<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }

    @Override
    public String toString() {
        return "Comic{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
