package me.indexyz.strap.utils;

import me.indexyz.strap.annotations.*;
import me.indexyz.strap.define.*;
import me.indexyz.strap.object.Update;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class UpdateExec {
    private static List<Class<Events>> classCache;
    private BotNetwork network;
    private Session session;

    public UpdateExec(BotNetwork network, Session session) {
        classCache = $.getAnnotations(Events.class);
        this.network = network;
        this.session = session;
        this.onInit();
    }

    private void onInit() {
        $.getMethods(classCache, OnInit.class).forEach(it -> {
            try {
                it.invoke(null, this.network);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

    public void execCommandUpdate(Update update) {
        List<Method> methods = $.getMethods(classCache, Command.class);

        CommandContext context = new CommandContext();
        context.network = this.network;
        context.update = update;
        context.session = session;

        for (Method method : methods) {
            try {
                Command command = method.getAnnotation(Command.class);

                // not response in group
                if (!command.responseInGroup() && !update.message.chat.type.equals(ChatType.PRIVATE)) {
                    continue;
                }

                if (update.message.text.startsWith("/" + command.value())) {
                    if (command.parseArgs()) {
                        List<String> args = Arrays.asList(update.message.text.split(" "));

                        if (args.size() > 1) {
                            context.args = args.subList(1, args.size());
                        }
                    }

                    method.invoke(null, context);
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public void execUserEvent(Update update, UserEventsKind kind) {
        UserEventContext context = new UserEventContext();
        context.network = this.network;
        context.update = update;
        context.session = session;

        List<Method> methods = $.getMethods(classCache, UserEvents.class);
        for (Method method : methods) {
            try {
                UserEvents command = method.getAnnotation(UserEvents.class);

                if (command.value() == kind) {
                    method.invoke(null, context);
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public void execMessageEvent(Update update) {
        MessageContext context = new MessageContext();
        context.network = this.network;
        context.update = update;
        context.session = session;

        List<Method> methods = $.getMethods(classCache, Message.class);

        for (Method method : methods) {
            try {
                method.invoke(null, context);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
