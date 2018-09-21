package me.indexyz.strap;

import me.indexyz.strap.annotations.Events;
import me.indexyz.strap.utils.$;
import me.indexyz.strap.utils.Configuration;

public class Main {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        Bot bot = Bot.create(configuration.getConfigs().getProperty("botToken"));

        Bot.logger.info("Found events class number: " + $.getAnnotations(Events.class).size());
        Bot.logger.info("Starting bot");
        bot.start();
    }
}
