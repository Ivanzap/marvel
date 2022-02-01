package com.ivanzap.marvel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "characters")
public class Character extends AbstractBaseEntity {

    @NotBlank
    @Column(name = "name", nullable = false)
    @Size(min = 1, max = 255)
    private String name;

    @NotBlank
    @Column(name = "description")
    @Size(min = 1, max = 255)
    private String description;

    public Character() {
    }

    public Character(Integer id, String name, String description) {
        super(id);
        this.name = name;
        this.description = description;
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

    @Override
    public String toString() {
        return "Character{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
