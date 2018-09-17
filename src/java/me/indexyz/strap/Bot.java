package me.indexyz.strap;

import me.indexyz.strap.annotations.Command;
import me.indexyz.strap.annotations.Events;
import me.indexyz.strap.define.AbstractContext;
import me.indexyz.strap.define.CommandContext;
import me.indexyz.strap.object.Update;
import me.indexyz.strap.utils.$;
import me.indexyz.strap.utils.BotNetwork;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.List;

public class Bot {
    private BotNetwork network;
    private Boolean enabled;
    private static Bot instance;

    private Bot() {
    }

    public static Bot get() {
        if (Bot.instance == null) {
            throw new RuntimeException("Bot is not created");
        }

        return Bot.instance;
    }

    public void init(String token) {
        this.network = new BotNetwork(token);
        this.classCache = $.<Events>getAnnotations(Events.class);
    }

    public static Bot create(String token) {
        Bot.instance = new Bot();
        Bot.instance.init(token);
        return get();
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    private static List<Class<Events>> classCache;

    public void start() {
        // Start pulling message
        this.enabled = true;
        long lastUpdateId = 0;

        while (this.enabled) {
            try {
                List<Update> updates = this.network.getUpdates(lastUpdateId);
                if (updates.size() != 0) {
                    lastUpdateId = updates.get(updates.size() - 1).update_id + 1;
                }

                updates.stream()
                    .forEach(update -> {
                        new Thread(() -> {
                            this.execUpdate(update);
                        }).start();
                    });
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void execUpdate(Update update) {
        if (update.message != null && update.message.text.startsWith("/")) {
            this.execCommandUpdate(update);
        }
    }

    private void execCommandUpdate(Update update) {
        List<Method> methods = $.getMethods(classCache, Command.class);
        for (Method method: methods) {
            try {
                Command command = method.getAnnotation(Command.class);
                CommandContext context = new CommandContext();
                context.network = this.network;
                context.update = update;

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
}
