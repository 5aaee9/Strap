package me.indexyz.strap;

import me.indexyz.strap.annotations.Events;
import me.indexyz.strap.define.Session;
import me.indexyz.strap.utils.$;
import me.indexyz.strap.utils.Configuration;

import java.util.Optional;
import java.util.ServiceLoader;

public class Main {
    public static void main(String[] args) {
        $.init();
        Configuration configuration = Configuration.get();

        // load plugins from dir
        Engine.loadDir("plugins");

        Session session = ServiceLoader.load(Session.class)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("unable to find session provider"));
        
        Bot bot = Bot.create(configuration.get("botToken"), session);

        Bot.logger.info("Found events class number: " + $.getAnnotations(Events.class).size());
        Bot.logger.info("Starting bot");
        bot.start();
    }
}
