package me.indexyz.strap.utils;

import com.google.common.collect.Lists;
import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class $ {
    private final static String[] DISABLE_CLASS_PREFIX = {
            "com.sun",
            "java.lang"
    };

    private static ArrayList<ClassPath.ClassInfo> getInfos() {
        ArrayList<ClassPath.ClassInfo> ret = Lists.newArrayList();
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            classLoop: for (final ClassPath.ClassInfo info : ClassPath.from(loader).getTopLevelClasses()) {
                for (final String prefix : $.DISABLE_CLASS_PREFIX) {
                    if (info.getPackageName().startsWith(prefix)) {
                        continue classLoop;
                    }
                }
                ret.add(info);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    public static <T> List<Class<T>> getAnnotations(Class<? extends Annotation> annotation) {
        return $.getInfos().stream()
                .map(ClassPath.ClassInfo::load)
                .filter(i -> i.isAnnotationPresent(annotation) && (!i.equals(annotation)))
                .map(i -> (Class<T>) i)
                .collect(Collectors.toList());
    }

    public static <T> List<Method> getMethods(Class<? extends Annotation> annotation) {
        return getMethods(
                $.getInfos().stream().map(ClassPath.ClassInfo::load)
                        .map(i -> ((Class<T>) i))
                        .collect(Collectors.toList()),
                annotation
        );
    }

    public static <T> List<Method> getMethods(Class<T> target, Class<? extends Annotation> annotation) {
        return getMethods(
            Arrays.asList(target),
            annotation
        );
    }

    public static <T> List<Method> getMethods(List<Class<T>> classes, Class<? extends Annotation> annotation) {
        return classes.stream()
            .map(i -> {
                return Arrays.asList(i.getMethods()).stream()
                    .filter(m -> m.getAnnotation(annotation) != null);
            })
            .flatMap(Function.identity())
            .collect(Collectors.toList());
    }
}
