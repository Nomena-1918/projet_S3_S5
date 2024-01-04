package org.example.demo.utils;

import java.util.List;

public class Cast {
    @SuppressWarnings("unchecked")
    public static <T> List<T> castToList(Object obj, Class<T> clazz) {
        if (obj instanceof List<?>) {
            return (List<T>) obj;
        }
        return null;
    }

    public static String castToString(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        }
        return null;
    }
}
