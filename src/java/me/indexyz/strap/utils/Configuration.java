package me.indexyz.strap.utils;

import java.io.*;
import java.util.Date;
import java.util.Properties;

public class Configuration {
    private static final String CONFIG_NAME = "config.properties";
    private Properties configs;

    public Configuration() {
        this.reload();
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

    private void createDefault() {
        Properties properties = new Properties();
        properties.setProperty("botToken", "");

        try {
            FileWriter writer = new FileWriter(CONFIG_NAME);
            Date date = new Date();
            properties.store(writer, "Strap bot config file");
        } catch (IOException e) {
            e.printStackTrace();
        }

        configs = properties;
    }

    public Properties getConfigs() {
        return configs;
    }
}
