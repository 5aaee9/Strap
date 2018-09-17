package me.indexyz.strap;

import me.indexyz.strap.annotations.Command;
import me.indexyz.strap.annotations.Events;
import me.indexyz.strap.define.CommandContext;
@Events
public class TestClass {
    @Command(value = "/start")
    public static void start(CommandContext context) {
        System.out.println(context.update.message.text);
        context.network.sendMessage(context.update.message.chat.id, "Hello, World!");
    }

    @Command(value = "/help", parseArgs = true)
    public static void help(CommandContext context) {
        if (context.args == null) {
            context.network.sendMessage(context.update.message.chat.id, "No args!");
        } else {
            context.network.sendMessage(context.update.message.chat.id, context.args.toString());
        }
    }
}
