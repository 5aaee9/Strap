package me.indexyz.strap;

import me.indexyz.strap.annotations.Command;
import me.indexyz.strap.annotations.Events;
import me.indexyz.strap.object.Update;

import java.util.List;

@Events
public class TestClass {
    @Command(value = "/start", parseArgs = true)
    public static void start(Update update, List<String> args) {

    }

    @Command("/help")
    public static void help(Update update) {

    }
}
