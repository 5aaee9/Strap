package me.indexyz.strap.utils

import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.util.*

class Configuration() {
    private val CONFIG_NAME = "config.properties"
    private var configs: Properties? = null

    init {
        this.reload()
    }

    companion object {
        private var instance: Configuration? = null
            get() {
                if (field == null) {
                    field = Configuration()
                }
                return field
            }
        fun get(): Configuration {
            return instance!!
        }
    }


    private fun reload() {
        try {
            val reader = FileReader(CONFIG_NAME)
            val properties = Properties()

            properties.load(reader)
            configs = properties
        } catch (e: IOException) {
            this.createDefault()
        }

    }

    fun save() {
        try {
            val writer = FileWriter(CONFIG_NAME)
            val date = Date()
            this.configs!!.store(writer, "Strap bot config file")
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun createDefault() {
        val properties = Properties()
        properties.setProperty("botToken", "")
        configs = properties

        this.save()
    }

    operator fun get(key: String): String {
        val envs = System.getProperties()
        return if (envs.containsKey(key.toUpperCase())) {
            envs.getProperty(key.toUpperCase())
        } else configs!!.getProperty(key)
    }

    fun has(key: String): Boolean {
        val envs = System.getProperties()
        return if (envs.containsKey(key.toUpperCase())) {
            true
        } else configs!!.containsKey(key)
    }

    operator fun set(key: String, value: String) {
        configs!!.setProperty(key, value)
    }
}