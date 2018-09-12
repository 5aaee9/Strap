package me.indexyz.strap.utils;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import me.indexyz.strap.object.Update;
import net.dongliu.requests.Header;
import net.dongliu.requests.Requests;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public JSONObject createReq(String path, JSONObject body) {
        String data = Requests.post(getRequestUrl(path))
                .body(body.toString())
                .headers(new Header("Content-Type", "application/json"))
                .send()
                .readToText();
        JSONObject object = new JSONObject(data);

        if (!object.getBoolean("ok")) {
            // throw exceptions
        }
        return object.getJSONObject("result");
    }

    public ArrayList<Update> getUpdates() {
        JSONObject resp = createReq("/getUpdates", new JSONObject());
        Gson gson = this.getGson();

        ArrayList<Update> returnList = Lists.newArrayList();

        resp.getJSONArray("").forEach(item -> {
            returnList.add(gson.fromJson(item.toString(), Update.class));
        });

        return returnList;
    }
}
