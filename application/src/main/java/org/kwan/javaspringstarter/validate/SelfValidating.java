package org.kwan.javaspringstarter.validate;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

@Slf4j
public abstract class SelfValidating {
    protected void validate() {
        Field[] fields = this.getClass().getDeclaredFields();

        for (Field field: fields) {
            if (field.isAnnotationPresent(NonNull.class)) {
                field.setAccessible(true);

                try {
                    Object value = field.get(this);

                    if (value == null) {
                        throw new IllegalArgumentException("Field " + field.getName() + " in " + this.getClass().getSimpleName() + "is required");
                    }
                } catch (IllegalAccessException e) {
                    log.error("Cannot access field " + field.getName() + " in " + this.getClass().getSimpleName());
                }
            }
        }
    }
}
