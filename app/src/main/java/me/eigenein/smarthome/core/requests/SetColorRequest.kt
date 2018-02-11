package me.eigenein.smarthome.core.requests

import android.graphics.Color
import me.eigenein.smarthome.core.Request
import me.eigenein.smarthome.core.states.MulticolorLightingState
import me.eigenein.smarthome.extensions.init
import org.json.JSONObject

data class SetColorRequest(private val red: Float, private val green: Float, private val blue: Float) : Request {
    override val payload = JSONObject().init {
        put("type", "SET_COLOR")
        put("red", red)
        put("green", green)
        put("blue", blue)
    }

    companion object {
        fun fromColor(color: Color) = SetColorRequest(color.red(), color.green(), color.blue())

        fun fromState(state: MulticolorLightingState) = SetColorRequest(state.red, state.green, state.blue)
    }
}
