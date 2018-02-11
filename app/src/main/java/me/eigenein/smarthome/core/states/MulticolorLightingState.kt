package me.eigenein.smarthome.core.states

import android.graphics.Color
import me.eigenein.smarthome.core.DeviceCustomState
import org.json.JSONObject

data class MulticolorLightingState(val red: Float, val green: Float, val blue: Float) : DeviceCustomState {

    constructor(response: JSONObject) : this(
        red = response.optDouble("red", 0.0).toFloat(),
        green = response.optDouble("green", 0.0).toFloat(),
        blue = response.optDouble("blue", 0.0).toFloat()
    )

    fun toColor(): Color = Color.valueOf(red, green, blue)
}
