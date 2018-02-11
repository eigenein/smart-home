package me.eigenein.smarthome.core.requests

import me.eigenein.smarthome.core.Request
import me.eigenein.smarthome.extensions.init
import org.json.JSONObject

data class TurnOffRequest(val unused: Unit = Unit) : Request {
    override val payload = PAYLOAD

    companion object {
        private val PAYLOAD = JSONObject().init {
            put("type", "TURN_OFF")
        }
    }
}
