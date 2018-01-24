package me.eigenein.smarthome

import me.eigenein.smarthome.extensions.init
import org.json.JSONObject

class RequestBuilder {
    companion object {
        fun ping() = JSONObject().init {
            this.put("type", "ping")
        }
    }
}
