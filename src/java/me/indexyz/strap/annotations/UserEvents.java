package me.indexyz.strap.annotations;

import me.indexyz.strap.define.UserEventsKind;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface UserEvents {
    UserEventsKind value();
}
