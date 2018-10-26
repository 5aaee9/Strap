package me.indexyz.strap.utils;

import java.io.*;
import java.util.Date;
import java.util.Properties;

public class Configuration {
    private static final String CONFIG_NAME = "config.properties";
    private Properties configs;

    private Configuration() {
        this.reload();
    }
    private static Configuration instance = null;

    public static Configuration get() {
        if (Configuration.instance == null) {
            Configuration.instance = new Configuration();
        }

        return Configuration.instance;
    }

    private void reload() {
        try {
            FileReader reader = new FileReader(CONFIG_NAME);
            Properties properties = new Properties();

            properties.load(reader);
            configs = properties;
        } catch (IOException e) {
            this.createDefault();
        }
    }

    public void save() {
        try {
            FileWriter writer = new FileWriter(CONFIG_NAME);
            Date date = new Date();
            this.configs.store(writer, "Strap bot config file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createDefault() {
        var properties = new Properties();
        properties.setProperty("botToken", "");
        configs = properties;

        this.save();
    }

    public String get(String key) {
        var envs = System.getProperties();
        if (envs.containsKey(key.toUpperCase())) {
            return envs.getProperty(key.toUpperCase());
        }
        return configs.getProperty(key);
    }

    public boolean has(String key) {
        var envs = System.getProperties();
        if (envs.containsKey(key.toUpperCase())) {
            return true;
        }
        return configs.containsKey(key);
    }

    public void set(String key, String value) {
        configs.setProperty(key, value);
    }
}
