package me.indexyz.strap
import me.indexyz.strap.define.UserEventsKind

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
public annotation class Events

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
public annotation class Message {

    @Retention(AnnotationRetention.RUNTIME)
    @Target(AnnotationTarget.FUNCTION)
    public annotation class Command(
            val command: String,
            val parseArgs: Boolean = false,
            val responseInGroup: Boolean = true
    )

    @Retention(AnnotationRetention.RUNTIME)
    @Target(AnnotationTarget.FUNCTION)
    public annotation class UserEvents(
            val kind: UserEventsKind
    )
}

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
public annotation class OnInit
