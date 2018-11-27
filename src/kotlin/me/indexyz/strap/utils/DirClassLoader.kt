package me.indexyz.strap.utils

import java.io.File
import java.io.IOException
import java.net.URL
import java.net.URLClassLoader
import java.util.*
import java.util.jar.JarFile
import java.util.stream.Collectors

class DirClassLoader {
    fun loadDir(dirName: String) {
        val dir = File(dirName)
        val jarFiles = Arrays.stream(Objects.requireNonNull(dir.listFiles()))
                .filter { it.isFile }
                .filter { it.name.endsWith(".jar") }
                .collect(Collectors.toList())

        jarFiles.forEach {
            try {
                val jarFile = JarFile(it)
                val e = jarFile.entries()
                val urls = arrayOf<URL>(it.toURI().toURL())

                val cl = URLClassLoader.newInstance(urls, DirClassLoader::class.java.classLoader)
                while (e.hasMoreElements()) {
                    val element = e.nextElement()

                    if (element.isDirectory() || !element.getName().endsWith(".class")) {
                        continue
                    }

                    // -6 because of .class
                    val className = element.name.substring(0, element.name.length - 6)
                            .replace('/', '.')
                    val clazz = cl.loadClass(className)

                    addClass(clazz)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            }

        }
    }
}