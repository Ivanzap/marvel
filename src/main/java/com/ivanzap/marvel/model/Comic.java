package com.ivanzap.marvel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

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

    @ManyToMany(mappedBy = "comics")
    @JsonIgnore
    private List<Character> characters;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Image image;

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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
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
