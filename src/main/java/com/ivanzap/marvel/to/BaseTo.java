package com.ivanzap.marvel.to;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ivanzap.marvel.HasId;

public class BaseTo implements HasId {
    @JsonIgnore
    protected Integer id;

    public BaseTo() {
    }

    public BaseTo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isNew() {
        return id == null;
    }
}
