package me.indexyz.strap.utils

import com.google.common.collect.Lists
import java.lang.reflect.Method
import java.util.*
import java.util.function.Function
import java.util.stream.Collectors
import java.util.stream.Stream
import kotlin.collections.ArrayList

private val cacheClasses = Lists.newArrayList<Class<*>>()
fun <T> addClass(clazz: Class<T>) {
    cacheClasses.add(clazz)
}

fun <T: Class<out Annotation>> findAnnotations(annotation: T): ArrayList<Class<*>> {
    val returnClasses: ArrayList<Class<*>> = Lists.newArrayList()

    cacheClasses.stream()
            .filter { Objects.nonNull(it) }
            .filter { it.isAnnotationPresent(annotation) && it != annotation }
            .forEach { returnClasses.add(it) }

    return returnClasses
}

fun findMethods(annotation: Class<Annotation>): ArrayList<Method> {
    return findMethods(
            cacheClasses.stream()
                    .collect(Collectors.toList()) as ArrayList<Class<*>>,
            annotation
    )
}

fun findMethods(target: Class<*>, annotation: Class<out Annotation>): ArrayList<Method> {
    return findMethods(
            listOf(target),
            annotation
    )
}

fun findMethods(classes: List<Class<*>>, annotation: Class<out Annotation>): ArrayList<Method> {
    val list: ArrayList<Method> = Lists.newArrayList()

    classes.stream()
            .map {
                Arrays.stream(it.methods)
                        .filter { m -> m.getAnnotation(annotation) != null }
            }
            .flatMap<Method>(Function.identity<Stream<Method>>())
            .forEach { list.add(it) }

    return list
}
