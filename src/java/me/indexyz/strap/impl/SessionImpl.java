package me.indexyz.strap.impl;

import me.indexyz.strap.define.Session;
import org.json.JSONObject;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Map;

public class SessionImpl implements Session {
    private static Map<String, JSONObject> sessionMapper = Collections.emptyMap();

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
