package me.eigenein.smarthome.core

import org.json.JSONObject

data class Response(
    val messageId: Int,
    val uuid: String,
    val deviceType: DeviceType,
    val rgbResponse: RGBResponse?
) {

    constructor(
        response: JSONObject,
        deviceType: DeviceType = DeviceType.valueOf(response.getString("deviceType"))
    ) : this(
        messageId = response.optInt("messageId", 0),
        uuid = response.getString("uuid"),
        deviceType = deviceType,
        rgbResponse = if (deviceType == DeviceType.RGB) RGBResponse(response) else null
    )

    data class RGBResponse(val red: Int, val green: Int, val blue: Int) {

        constructor(response: JSONObject) : this(
            red = response.optInt("red", 0),
            green = response.optInt("green", 0),
            blue = response.optInt("blue", 0)
        )
    }
}
