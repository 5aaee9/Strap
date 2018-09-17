package me.indexyz.strap.define;

import me.indexyz.strap.object.Update;
import me.indexyz.strap.utils.BotNetwork;

import java.util.List;

public class CommandContext implements AbstractContext {
    public BotNetwork network;
    public List<String> args = null;
    public Update update;
}
