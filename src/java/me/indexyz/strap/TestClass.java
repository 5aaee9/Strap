package me.indexyz.strap;

import me.indexyz.strap.annotations.Command;
import me.indexyz.strap.annotations.Events;
import me.indexyz.strap.object.Update;
import me.indexyz.strap.utils.BotNetwork;

import java.util.List;

@Events
public class TestClass {
    @Command(value = "/start")
    public static void start(Update update, BotNetwork network) {
        System.out.println(update.message.text);
        network.sendMessage(update.message.chat.id, "Hello, World!");
    }

    @Command(value = "/help", parseArgs = true)
    public static void help(Update update, BotNetwork network, List<String> args) {
        if (args == null) {
            network.sendMessage(update.message.chat.id, "No args!");
        } else {
            network.sendMessage(update.message.chat.id, args.toString());
        }
    }
}
