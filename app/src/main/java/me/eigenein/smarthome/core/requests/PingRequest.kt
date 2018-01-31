package me.eigenein.smarthome.core.requests

import me.eigenein.smarthome.core.DeviceAddress
import me.eigenein.smarthome.core.Request
import me.eigenein.smarthome.extensions.init
import org.json.JSONObject

data class PingRequest(override val address: DeviceAddress) : Request {
    override val payload = JSONObject().init {
        this.put("type", "ping")
    }
}
