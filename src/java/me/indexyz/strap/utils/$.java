package me.indexyz.strap.utils;

import com.google.common.collect.Lists;
import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;
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
    public static <T extends Class> List<Class<? extends T>> getAnnotations(T annotation) {
        return $.getInfos().stream()
                .map(i -> i.load())
                .filter(i -> i.isAnnotationPresent(annotation) && (!i.equals(annotation)))
                .map(i -> (Class<? extends T>) i)
                .collect(Collectors.toList());

    }

    public static <T extends Class> List<Method> getMethods(List<T> classes, Class<? extends Annotation> annotation) {
        return classes.stream()
                .map(i -> {
                    return Arrays.asList(i.getMethods()).stream()
                        .filter(m -> m.getAnnotation(annotation) != null);
                })
                .flatMap(Function.identity())
                .collect(Collectors.toList());
    }

    public static <T extends Class> List<Method> getMethods(Class annotation) {
        return getMethods(
                $.getInfos().stream().map(ClassPath.ClassInfo::load).collect(Collectors.toList()),
                annotation
        );
    }
}
