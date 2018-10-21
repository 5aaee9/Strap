package me.indexyz.strap.define;

import me.indexyz.strap.object.Update;
import me.indexyz.strap.utils.BotNetwork;
import me.indexyz.strap.utils.Configuration;

public class AbstractContext {
    public BotNetwork network;
    public Update update;
    public Session session;
    public Configuration configuration;

    public void copy(AbstractContext context) {
        this.network = context.network;
        this.update = context.update;
        this.session = context.session;
        this.configuration = context.configuration;
    }
}
