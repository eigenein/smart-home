package me.eigenein.smarthome.core

import me.eigenein.smarthome.extensions.toDatagramPacket
import org.json.JSONObject
import java.net.InetAddress

interface Request {
    val address: DeviceAddress
    val payload: JSONObject

    fun toDatagramPacket(host: InetAddress, port: Int) = payload
        .toString()
        .toByteArray(Charsets.UTF_8)
        .toDatagramPacket(host, port)
}
