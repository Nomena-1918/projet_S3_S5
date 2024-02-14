package org.voyage.demo.utils;

import java.util.List;

public class Cast {
    @SuppressWarnings("unchecked")
    public static <T> List<T> castToList(Object obj) {
        if (obj instanceof List<?>) {
            return (List<T>) obj;
        }
        return null;
    }
}
