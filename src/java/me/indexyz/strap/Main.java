package me.indexyz.strap;

import me.indexyz.strap.annotations.Events;
import me.indexyz.strap.utils.$;

public class Main {
    public static void main(String[] args) {
        Bot bot = Bot.create("");
        Bot.logger.info("Found events class number: " + $.getAnnotations(Events.class).size());
        Bot.logger.info("Starting bot");
        bot.start();
    }
}
