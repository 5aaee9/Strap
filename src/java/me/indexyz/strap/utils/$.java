package me.indexyz.strap.utils;

import com.google.common.collect.Lists;
import com.google.common.reflect.ClassPath;
import me.indexyz.strap.Bot;

import java.io.IOException;
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

    public static void init() {
        ArrayList<ClassPath.ClassInfo> ret = Lists.newArrayList();
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();

        try {
            classLoop:
            for (final ClassPath.ClassInfo info : ClassPath.from(loader).getAllClasses()) {
                for (final String prefix : $.DISABLE_CLASS_PREFIX) {
                    if (info.getPackageName().startsWith(prefix)) {
                        continue classLoop;
                    }
                }

                if (info.getPackageName().startsWith("me.indexyz.telegram")) {
                    Bot.logger.info(info.getPackageName());
                }


                try {
                    $.classes.add(info.load());
                } catch (Throwable e) {
                    continue ;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
