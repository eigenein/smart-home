package me.eigenein.smarthome.extensions

import android.util.Log
import io.reactivex.Completable
import io.reactivex.Maybe
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.SocketTimeoutException

private val TAG = DatagramSocket::class.java.simpleName

fun DatagramSocket.sendCompletable(packet: DatagramPacket) : Completable = Completable.create {
    send(packet)
    it.onComplete()
}

// TODO: listen for incoming datagrams.

fun DatagramSocket.receiveMaybe(length: Int) : Maybe<DatagramPacket> = Maybe.create<DatagramPacket> {
    val buffer = ByteArray(length)
    val packet = DatagramPacket(buffer, buffer.size)
    try {
        receive(packet)
        Log.d(TAG, "Received ${packet.length} bytes from ${packet.socketAddress}")
        it.onSuccess(packet)
    } catch (e: SocketTimeoutException) {
        Log.e(TAG, "Timeout while receiving a packet on $localSocketAddress")
        it.onComplete()
    }
}
