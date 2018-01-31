package me.eigenein.smarthome.extensions

import android.util.Log
import me.eigenein.smarthome.core.DeviceAddress
import me.eigenein.smarthome.core.DeviceType
import me.eigenein.smarthome.core.Response
import me.eigenein.smarthome.core.statuses.MulticolorLightingStatus
import java.net.DatagramPacket
import java.net.DatagramSocket

private val TAG = DatagramSocket::class.java.simpleName

fun DatagramPacket.toByteArray() = data.sliceArray(0..length)

fun DatagramPacket.sendTo(socket: DatagramSocket) {
    Log.d(TAG, "Sending $length bytes to $socketAddress")
    socket.send(this)
}

fun DatagramPacket.toResponse() : Response {
    val payload = this.toByteArray().toString(Charsets.UTF_8).toJSONObject()
    val deviceType = DeviceType.valueOf(payload.getString("deviceType"))
    return Response(
        deviceAddress = DeviceAddress.from(this),
        messageId = payload.optInt("messageId", 0),
        uuid = payload.getString("uuid"),
        deviceType = deviceType,
        status = when (deviceType) {
            DeviceType.MulticolorLighting -> MulticolorLightingStatus(payload)
        }
    )
}
