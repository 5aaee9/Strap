package me.indexyz.strap;

import me.indexyz.strap.annotations.Events;
import me.indexyz.strap.define.Session;
import me.indexyz.strap.utils.$;
import me.indexyz.strap.utils.Configuration;

import java.util.Optional;
import java.util.ServiceLoader;

public class Main {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        Session session = Optional.of(
                ServiceLoader.load(Session.class)
                    .iterator()
                    .next()
            )
            .orElseThrow(() -> new IllegalStateException("Cant found session provider"));

        Bot bot = Bot.create(configuration.getConfigs().getProperty("botToken"), session);

        Bot.logger.info("Found events class number: " + $.getAnnotations(Events.class).size());
        Bot.logger.info("Starting bot");
        bot.start();
    }
}
