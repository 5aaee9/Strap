package me.indexyz.strap.impl

import me.indexyz.strap.define.Session
import org.json.JSONObject
import java.util.HashMap

class SessionImpl: Session {
    override operator fun get(key: String): JSONObject? {
        return sessionMapper.get(key)
    }

    override operator fun set(key: String, `object`: JSONObject) {
        SessionImpl.sessionMapper[key] = `object`
    }

    companion object {
        private val sessionMapper = HashMap<String, JSONObject>()
    }
}
