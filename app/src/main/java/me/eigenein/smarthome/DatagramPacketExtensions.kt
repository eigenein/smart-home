package me.eigenein.smarthome

import org.json.JSONObject
import java.net.DatagramPacket

fun DatagramPacket.toJSONObject() : JSONObject {
    return JSONObject(data.sliceArray(0..length).toString(Charsets.UTF_8))
}
