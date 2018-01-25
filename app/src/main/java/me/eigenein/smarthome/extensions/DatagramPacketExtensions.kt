package me.eigenein.smarthome.extensions

import android.util.Log
import java.net.DatagramPacket
import java.net.DatagramSocket

private val TAG = DatagramSocket::class.java.simpleName

fun DatagramPacket.toByteArray() = data.sliceArray(0..length)

fun DatagramPacket.sendTo(socket: DatagramSocket) {
    Log.d(TAG, "Sending $length bytes to $socketAddress")
    socket.send(this)
}
