package me.indexyz.strap.utils;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import me.indexyz.strap.object.Message;
import me.indexyz.strap.object.Update;
import net.dongliu.requests.Header;
import net.dongliu.requests.Requests;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class BotNetwork {
    private String token;

    public BotNetwork(String token) {
        this.token = token;
    }

    public String getRequestUrl(String path) {
        StringBuilder builder = new StringBuilder();
        builder.append("https://api.telegram.org/bot")
                .append(this.token)
                .append(path);
        return builder.toString();
    }

    private Gson gson;
    private Gson getGson() {
        if (this.gson == null) {
            this.gson = new Gson();
        }
        return this.gson;
    }

    @Nullable
    public String sendReq(String path, JSONObject body) {
        String data = Requests.post(getRequestUrl(path))
                .body(body.toString())
                .headers(new Header("Content-Type", "application/json"))
                .send()
                .readToText();
        JSONObject object = new JSONObject(data);
        if (!object.getBoolean("ok")) {
            return null;
        }
        return object.get("result").toString();
    }

    public ArrayList<Update> getUpdates(long id) {
        String result = sendReq("/getUpdates", (new JSONObject()).put("offset", id));
        if (result == null) {
            return Lists.newArrayList();
        }

        JSONArray resp = new JSONArray(result);
        Gson gson = this.getGson();

        ArrayList<Update> returnList = Lists.newArrayList();

        resp.forEach(item -> {
            System.out.println(item.toString());
            returnList.add(gson.fromJson(item.toString(), Update.class));
        });

        return returnList;
    }

    public <T> Message sendMessage(T chatId, String text, String parseMode, boolean disableWebPagePreview, boolean disableNotification, Long replyToMessage_id) {
        JSONObject object = new JSONObject();
        object.put("chat_id", chatId);
        object.put("text", text);
        object.put("parse_mode", parseMode);
        object.put("disable_web_page_preview", disableWebPagePreview);
        object.put("disable_notification", disableNotification);
        object.put("reply_to_message_id", replyToMessage_id);

        String resp = sendReq("/sendMessage", object);
        return this.getGson().fromJson(resp, Message.class);
    }

    public <T> Message sendMessage(T chatId, String text, String parseMode, boolean disableWebPagePreview, boolean disableNotification) {
        return sendMessage(chatId, text, parseMode, disableWebPagePreview, disableNotification, null);
    }

    public <T> Message sendMessage(T chatId, String text, String parseMode, boolean disableWebPagePreview) {
        return sendMessage(chatId, text, parseMode, disableWebPagePreview, false, null);
    }

    public <T> Message sendMessage(T chatId, String text, String parseMode) {
        return sendMessage(chatId, text, parseMode, false, false, null);
    }

    public <T> Message sendMessage(T chatId, String text) {
        return sendMessage(chatId, text, "Markdown", false, false, null);
    }

    public <T> void kickChatMe(T chatId, long userId, long untilDate) {
        JSONObject object = new JSONObject();
        object.put("chat_id", chatId);
        object.put("user_id", userId);
        object.put("until_date", untilDate);

        sendReq("/kickChatMember", object);
    }

    public <T> void kickChatMe(T chatId, long userId) {
        kickChatMe(chatId, userId, 0);
    }

    public <T> void unbanChatMember(T chatId, long userId) {
        JSONObject object = new JSONObject();
        object.put("chat_id", chatId);
        object.put("user_id", userId);

        sendReq("/unbanChatMember", object);
    }
}
