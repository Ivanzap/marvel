package com.ivanzap.marvel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "comics")
public class Comic extends AbstractBaseEntity {

    @NotBlank
    @Column(name = "title")
    @Size(min = 1, max = 255)
    private String title;

    @NotBlank
    @Column(name = "description")
    @Size(min = 1, max = 255)
    private String description;

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

    @Override
    public String toString() {
        return "Comic{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
