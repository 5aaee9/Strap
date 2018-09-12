package me.indexyz.strap;

import me.indexyz.strap.annotations.Command;
import me.indexyz.strap.annotations.Events;
import me.indexyz.strap.object.Message;
import me.indexyz.strap.utils.$;

public class Main {
    public static void main(String[] args) {
        Bot bot = Bot.create("663045071:AAHi-GZU-AoYLMX9HjOA1SfQsPCFIcH3Ikk");

        System.out.println($.getAnnotations(Events.class).size());
        System.out.println($.getMethods(Command.class).size());

        System.out.println("Hello, World!");
    }
}
