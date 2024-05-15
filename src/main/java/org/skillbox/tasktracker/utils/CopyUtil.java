package org.skillbox.tasktracker.utils;

import lombok.SneakyThrows;

import java.lang.reflect.Field;

public class CopyUtil {

    @SneakyThrows
    public static void nullNotCopy(Object source, Object target){
        Class<?> clazz = source.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields){
            field.setAccessible(true);
            Object value = field.get(source);
            if (value != null){
                 field.set(target, value);
            }
        }
    }
}
