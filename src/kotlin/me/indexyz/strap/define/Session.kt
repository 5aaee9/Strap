package me.indexyz.strap.define

import org.json.JSONObject

interface Session {
    abstract operator fun get(key: String): JSONObject?
    abstract operator fun set(key: String, `object`: JSONObject)
}

