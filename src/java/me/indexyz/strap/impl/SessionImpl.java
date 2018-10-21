package me.indexyz.strap.impl;

import me.indexyz.strap.define.Session;
import org.json.JSONObject;

import javax.annotation.Nonnull;
import java.util.HashMap;

public class SessionImpl implements Session {
    private static HashMap<String, JSONObject> sessionMapper = new HashMap<String, JSONObject>();

    @Override
    @Nonnull
    public JSONObject get(String key) {
        return SessionImpl.sessionMapper.get(key);
    }

    @Override
    public void set(String key, JSONObject object) {
        SessionImpl.sessionMapper.put(key, object);
    }
}
