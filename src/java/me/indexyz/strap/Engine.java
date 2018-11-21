package me.indexyz.strap;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class Engine {
    public static void loadDir(String dirName) {
        var dir = new File(dirName);
        var jarFiles = Arrays.stream(Objects.requireNonNull(dir.listFiles()))
                .filter(File::isFile)
                .filter(i -> i.getName().endsWith(".jar"))
                .collect(Collectors.toList());

        jarFiles.forEach(jarPath -> {
            try {
                var jarFile = new JarFile(jarPath);
                Enumeration<JarEntry> e = jarFile.entries();
                URL[] urls = new URL[]{jarPath.toURI().toURL()};

                var cl = URLClassLoader.newInstance(urls, Engine.class.getClassLoader());
                while (e.hasMoreElements()) {
                    var element = e.nextElement();

                    if(element.isDirectory() || !element.getName().endsWith(".class")){
                        continue;
                    }

                    // -6 because of .class
                    var className = element.getName().substring(0, element.getName().length() - 6)
                        .replace('/', '.');
                    var clazz = cl.loadClass(className);

                    $.addClass(clazz);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });


    }
}
