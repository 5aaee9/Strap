package me.indexyz.strap.object;

import java.util.List;

public class Message {
    public long message_id;

    public User from;
    public Chat chat;

    public long date;
    public String text;

    public User forward_from;
    public Chat forward_from_chat;
    public int forward_from_message_id;
    public String forward_signature;
    public int forward_date;
    public Message reply_to_message;
    public int edit_date;
    public String media_group_id;
    public String author_signature;

    public List<User> new_chat_members;
}
