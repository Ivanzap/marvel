package com.ivanzap.marvel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "images")
public class Image extends AbstractBaseEntity {

    @Column(name = "path")
    @Size(max = 255, message = "Size of path should be below 255")
    private String path;

    public Image() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", path='" + path + '\'' +
                '}';
    }
}
