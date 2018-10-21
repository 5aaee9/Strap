package me.indexyz.strap;

import me.indexyz.strap.annotations.Command;
import me.indexyz.strap.annotations.Events;
import me.indexyz.strap.annotations.UserEvents;
import me.indexyz.strap.define.CommandContext;
import me.indexyz.strap.define.UserEventContext;
import me.indexyz.strap.define.UserEventsKind;

import java.util.Random;

@Events
public class TestClass {
    @Command("start")
    public static void start(CommandContext context) {
        context.network.sendMessage(context.update.message.chat.id, "Hello, World!");
    }

    @Command(value = "help", parseArgs = true)
    public static void help(CommandContext context) {
        if (context.args == null) {
            context.network.sendMessage(context.update.message.chat.id, "No args!");
        } else {
            context.network.sendMessage(context.update.message.chat.id, context.args.toString());
        }
    }

    @UserEvents(UserEventsKind.JOINED_CHAT)
    public static void welcomeMessage(UserEventContext context) {
        String username = context.update.message.new_chat_members.stream()
                .map(u -> u.first_name)
                .reduce("", (origin, name) -> origin + " " + name)
                .trim();

        context.network.sendMessage(context.update.message.chat.id, "Welcome " + username + " joined this group!");
    }

    @Command("prpr")
    public static void prpr(CommandContext context) {
        String[] values = new String[]{
                "⁄(⁄ ⁄•⁄ω⁄•⁄ ⁄)⁄", "(≧ ﹏ ≦)", "(*/ω＼*)", "ヽ(*。>Д<)o゜", "(つ ﹏ ⊂)", "( >  < )"
        };
        context.network.sendMessage(context.update.message.chat.id, values[(new Random()).nextInt(values.length)]);
    }
}
