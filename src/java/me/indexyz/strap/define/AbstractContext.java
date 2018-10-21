package me.indexyz.strap.define;

import me.indexyz.strap.object.Update;
import me.indexyz.strap.utils.BotNetwork;
import me.indexyz.strap.utils.Configuration;

public class AbstractContext {
    public BotNetwork network;
    public Update update;
    public Session session;
    public Configuration configuration;
}
