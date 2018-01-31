package me.eigenein.smarthome.extensions

import android.util.Log
import java.net.DatagramPacket
import java.net.DatagramSocket

private val TAG = DatagramSocket::class.java.simpleName

fun DatagramSocket.receive(length: Int): DatagramPacket {
    val buffer = ByteArray(length)
    val packet = DatagramPacket(buffer, buffer.size)
    receive(packet)
    Log.d(TAG, "Received ${packet.length} bytes from ${packet.socketAddress}")
    return packet
}
