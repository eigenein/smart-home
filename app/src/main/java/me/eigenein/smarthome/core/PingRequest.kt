package me.eigenein.smarthome.core

import me.eigenein.smarthome.extensions.init
import org.json.JSONObject

class PingRequest(address: DeviceAddress) : Request(address) {
    override val payload = JSONObject().init {
        this.put("type", "ping")
    }
}
