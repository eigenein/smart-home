package me.eigenein.smarthome.core.statuses

import me.eigenein.smarthome.core.Status
import org.json.JSONObject

data class MulticolorLightingStatus(val red: Int, val green: Int, val blue: Int) : Status {

    constructor(response: JSONObject) : this(
        red = response.optInt("red", 0),
        green = response.optInt("green", 0),
        blue = response.optInt("blue", 0)
    )
}
