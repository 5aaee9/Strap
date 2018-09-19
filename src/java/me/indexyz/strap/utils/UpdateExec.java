package me.indexyz.strap.utils;

import me.indexyz.strap.annotations.Command;
import me.indexyz.strap.annotations.Events;
import me.indexyz.strap.annotations.UserEvents;
import me.indexyz.strap.define.ChatType;
import me.indexyz.strap.define.CommandContext;
import me.indexyz.strap.define.UserEventContext;
import me.indexyz.strap.define.UserEventsKind;
import me.indexyz.strap.object.Update;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class UpdateExec {
    private static List<Class<Events>> classCache;
    private BotNetwork network;

    public UpdateExec(BotNetwork network) {
        classCache = $.getAnnotations(Events.class);
        this.network = network;
    }

    public void execCommandUpdate(Update update) {
        List<Method> methods = $.getMethods(classCache, Command.class);
        for (Method method: methods) {
            try {
                Command command = method.getAnnotation(Command.class);
                CommandContext context = new CommandContext();
                context.network = this.network;
                context.update = update;

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

        List<Method> methods = $.getMethods(classCache, UserEvents.class);
        for (Method method: methods) {
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
}
