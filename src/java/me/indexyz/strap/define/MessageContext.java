package me.indexyz.strap.define;

public class MessageContext extends AbstractContext {
    public void reply(String message) {
        this.network.sendMessage
                (this.update.message.chat.id, message);
    }
}
