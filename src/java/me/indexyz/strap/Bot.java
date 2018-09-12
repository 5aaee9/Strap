package me.indexyz.strap;

import me.indexyz.strap.annotations.Command;
import me.indexyz.strap.annotations.Events;
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
                        .filter(i -> i.message != null)
                        .filter(i -> i.message.text.startsWith("/"))
                        .forEach(update -> {
                            List<Method> methods = $.getMethods(classCache, Command.class);
                            methods.stream().forEach(method -> {
                                Command command = method.getAnnotation(Command.class);
                                if (!update.message.text.startsWith(command.value())) {
                                    return;
                                }
                                new Thread(() -> {
                                    try {
                                        if (command.parseArgs()) {
                                            List<String> args = Arrays.asList(update.message.text.split(" "));

                                            if (args.size() > 1) {
                                                args = args.subList(1, args.size());
                                            } else {
                                                args = null;
                                            }

                                            method.invoke(null, update, this.network, args);
                                        } else {
                                            method.invoke(null, update, this.network);
                                        }

                                    } catch (IllegalAccessException | InvocationTargetException e) {
                                        e.printStackTrace();
                                    }
                                }).start();
                            });
                        });
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
