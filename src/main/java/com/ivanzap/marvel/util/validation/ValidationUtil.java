package com.ivanzap.marvel.util.validation;

import com.ivanzap.marvel.HasId;

public class ValidationUtil {
    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalArgumentException(bean + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(HasId bean, int id) {
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.getId() != id) {
            throw new IllegalArgumentException(bean + " must be with id=" + id);
        }
    }

    public static String checkSortCharacter(String sort) {
        if(sort.equals("name") || sort.equals("description")) {
            return sort;
        } else {
            return "id";
        }
    }

    public static String checkSortComic(String sort) {
        if(sort.equalsIgnoreCase("title") || sort.equalsIgnoreCase("description")) {
            return sort;
        } else {
            return "id";
        }
    }
}
