package me.eigenein.smarthome

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
        response.optInt("messageId", 0),
        response.getString("uuid"),
        deviceType,
        if (deviceType == DeviceType.RGB) RGBResponse(response) else null
    )

    data class RGBResponse(val red: Int, val green: Int, val blue: Int) {

        constructor(response: JSONObject) : this(
            response.optInt("red", 0),
            response.optInt("green", 0),
            response.optInt("blue", 0)
        )
    }

    enum class DeviceType(descriptionResourceId: Int) {
        RGB(R.string.device_type_rgb)
    }
}
