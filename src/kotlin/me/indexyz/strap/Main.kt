package me.indexyz.strap

import me.indexyz.strap.define.Session
import me.indexyz.strap.utils.Configuration
import me.indexyz.strap.utils.DirClassLoader
import me.indexyz.strap.utils.findAnnotations
import java.util.*

fun main() {
    val configuration = Configuration.get()

    // load plugins from dir
    val loader = DirClassLoader()
    loader.loadDir("plugins")

    val session = ServiceLoader.load(Session::class.java)
            .findFirst()
            .orElseThrow { IllegalStateException("unable to find session provider") }

    val bot = Bot.create(configuration["botToken"], session)

    Bot.logger.info("Found events class number: " + findAnnotations(Annotations.Events::class.java).size)
    Bot.logger.info("Starting bot")
    bot.start()
}