package me.indexyz.strap;

import me.indexyz.strap.utils.BotNetwork;
import net.dongliu.requests.Requests;
import java.util.HashMap;

public class Bot {
    private BotNetwork network;
    private Boolean enabled;
    private static Bot instance;

    private Bot() {}

    public static Bot get() {
        if (Bot.instance == null) {
            throw new RuntimeException("Bot is not created");
        }

        return Bot.instance;
    }

    public static Bot create(String token) {
        Bot.instance = new Bot();
        Bot.instance.setNetwork(new BotNetwork(token));
        return get();
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }


    public void start() {
        // Start pulling message
        this.enabled = true;
        while (this.enabled) {
            // Pulling interval
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setNetwork(BotNetwork network) {
        this.network = network;

    }
}
