package me.indexyz.strap.utils;

import com.google.common.collect.Lists;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class $ {
    private final static String[] DISABLE_CLASS_PREFIX = {
            "com.sun",
            "java.lang",
            "com.fasterxml"
    };

    private static ArrayList<Class> classes = Lists.newArrayList();

    public static void addClass(Class clazz) {
        $.classes.add(clazz);
    }

    @SuppressWarnings("unchecked")
    public static <T> List<Class<T>> getAnnotations(Class<? extends Annotation> annotation) {
        return $.classes.stream()
                .filter(Objects::nonNull)
                .filter(i -> i.isAnnotationPresent(annotation) && (!i.equals(annotation)))
                .map(i -> (Class<T>) i)
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public static <T> List<Method> getMethods(Class<? extends Annotation> annotation) {
        return getMethods(
                $.classes.stream()
                        .map(i -> ((Class<T>) i))
                        .collect(Collectors.toList()),
                annotation
        );
    }

    public static <T> List<Method> getMethods(Class<T> target, Class<? extends Annotation> annotation) {
        return getMethods(
                Collections.singletonList(target),
                annotation
        );
    }

    public static <T> List<Method> getMethods(List<Class<T>> classes, Class<? extends Annotation> annotation) {
        return classes.stream()
                .map(i -> {
                    return Arrays.stream(i.getMethods())
                            .filter(m -> m.getAnnotation(annotation) != null);
                })
                .flatMap(Function.identity())
                .collect(Collectors.toList());
    }
}
