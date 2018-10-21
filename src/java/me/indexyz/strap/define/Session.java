package me.indexyz.strap.define;

import org.json.JSONObject;

public interface Session {
    public JSONObject get(String key);

    public void set(String key, JSONObject object);
}
