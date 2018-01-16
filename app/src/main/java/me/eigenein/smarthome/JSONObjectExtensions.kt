package me.eigenein.smarthome

import org.json.JSONObject
import java.net.DatagramPacket
import java.net.InetAddress

fun JSONObject.toDatagramPacket(host: InetAddress, port: Int): DatagramPacket {
    val buffer = toString().toByteArray()
    return DatagramPacket(buffer, buffer.size, host, port)
}
