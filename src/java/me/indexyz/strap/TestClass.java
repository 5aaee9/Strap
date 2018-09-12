package me.indexyz.strap;

import me.indexyz.strap.annotations.Command;
import me.indexyz.strap.annotations.Events;
import me.indexyz.strap.object.Update;
import me.indexyz.strap.utils.BotNetwork;

import java.util.List;

@Events
public class TestClass {
    @Command(value = "/start", parseArgs = true)
    public static void start(Update update, BotNetwork network) {
        System.out.println(update.message.text);
    }
}
