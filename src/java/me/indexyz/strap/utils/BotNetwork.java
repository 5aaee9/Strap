package me.indexyz.strap.utils;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import me.indexyz.strap.Bot;
import me.indexyz.strap.exceptions.UpdateFailure;
import me.indexyz.strap.object.Message;
import me.indexyz.strap.object.Update;
import okhttp3.*;
import org.json.JSONObject;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private static OkHttpClient client = new OkHttpClient();

    private JSONObject sendReq(String path, RequestBody body) {
        return sendReq(path, body, 0);
    }

    private JSONObject sendReq(String path, RequestBody body, int depth) {
        Request request = new Request.Builder()
                .url(getRequestUrl(path))
                .header("Content-Type", "application/json")
                .post(body)
                .build();

        try {
            if (depth < 5) {
                Response response = client.newCall(request).execute();
                assert response.body() != null;
                var respBody = response.body().string();

                response.close();
                var object = new JSONObject(respBody);

                if (!object.getBoolean("ok") && object.has("result")) {
                    Bot.logger.info(respBody);
                    return sendReq(path, body, depth + 1);
                }

                return object;
            } else {
                throw new UpdateFailure();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new UpdateFailure();
        }
    }

    @Nullable
    public ArrayList<Update> getUpdates(long id) {
        var resp = sendReq("/getUpdates", createFromJson((new JSONObject()).put("offset", id)));

        Gson gson = this.getGson();
        ArrayList<Update> returnList = Lists.newArrayList();

        resp.getJSONArray("result").forEach(item -> {
            returnList.add(gson.fromJson(item.toString(), Update.class));
        });

        return returnList;
    }

    private RequestBody createFromJson(JSONObject json) {
        return RequestBody.create(JSON, json.toString());
    }

    public <T> Message sendMessage(T chatId, String text, String parseMode, boolean disableWebPagePreview, boolean disableNotification, Long replyToMessage_id) {
        JSONObject object = new JSONObject();
        object.put("chat_id", chatId);
        object.put("text", text);
        object.put("parse_mode", parseMode);
        object.put("disable_web_page_preview", disableWebPagePreview);
        object.put("disable_notification", disableNotification);
        object.put("reply_to_message_id", replyToMessage_id);

        var resp = sendReq("/sendMessage", createFromJson(object));
        return this.getGson().fromJson(resp.getJSONObject("result").toString(), Message.class);
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

    public <T> void kickChatMember(T chatId, long userId, long untilDate) {
        JSONObject object = new JSONObject();
        object.put("chat_id", chatId);
        object.put("user_id", userId);
        object.put("until_date", untilDate);

        sendReq("/kickChatMember", createFromJson(object));
    }

    public <T> void kickChatMember(T chatId, long userId) {
        kickChatMember(chatId, userId, 0);
    }

    public <T> void unbanChatMember(T chatId, long userId) {
        JSONObject object = new JSONObject();
        object.put("chat_id", chatId);
        object.put("user_id", userId);

        sendReq("/unbanChatMember", createFromJson(object));
    }

    private String detectType(Path path) {
        try {
            return Files.probeContentType(path);
        } catch (IOException e) {
            e.printStackTrace();

            return "application/octet-stream";
        }
    }

//    public Message sendDocument(String chatId, Path document, Path thumb, String caption, String parse_mode, ) {
//        return null;
//    }
}
