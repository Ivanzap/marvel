package com.ivanzap.marvel.util;

import com.ivanzap.marvel.HasId;
import com.ivanzap.marvel.model.AbstractBaseEntity;
import com.ivanzap.marvel.to.BaseTo;

public class ValidationUtil {
    public static void checkNew(AbstractBaseEntity bean) {
        if (!bean.isNew()) {
            throw new IllegalArgumentException(bean + " must be new (id=null)");
        }
    }

    public static void checkNew(BaseTo bean) {
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

}
