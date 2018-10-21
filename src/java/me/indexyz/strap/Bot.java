package me.indexyz.strap;

import me.indexyz.strap.define.Session;
import me.indexyz.strap.define.UserEventsKind;
import me.indexyz.strap.object.Update;
import me.indexyz.strap.utils.BotNetwork;
import me.indexyz.strap.utils.UpdateExec;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Bot {
    private BotNetwork network;
    private boolean enabled;
    private static Bot instance;
    private UpdateExec execer;
    public static final Logger logger = LogManager.getLogger(Bot.class);

    private Bot() {
    }

    public static Bot get() {
        if (Bot.instance == null) {
            throw new RuntimeException("Bot is not created");
        }

        return Bot.instance;
    }

    public BotNetwork getNetwork() {
        return network;
    }

    public void init(String token, Session session) {
        this.network = new BotNetwork(token);
        this.execer = new UpdateExec(this.network, session);
    }

    public static Bot create(String token, Session session) {
        Bot.instance = new Bot();
        Bot.instance.init(token, session);
        return get();
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void start() {
        // Start pulling message
        this.enabled = true;
        long lastUpdateId = 0;

        while (this.enabled) {
            try {
                List<Update> updates = this.network.getUpdates(lastUpdateId);
                if (updates == null) {
                    logger.error("Unable to get updates, check your token.");
                    Thread.sleep(1000);
                    continue;
                }

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
        if (update.message != null && update.message.text != null) {
            if (update.message.text.startsWith("/")) {
                this.execer.execCommandUpdate(update);
            } else {
                this.execer.execMessageEvent(update);
            }
        }

        if (update.message != null && update.message.new_chat_members != null) {
            this.execer.execUserEvent(update, UserEventsKind.JOINED_CHAT);
        }
    }
}
