package me.eigenein.smarthome

import io.reactivex.Completable
import io.reactivex.Maybe
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.SocketTimeoutException

class DatagramSocketHelper(init: DatagramSocket.() -> Unit) {
    private val socket = DatagramSocket()

    init {
        socket.init()
    }

    fun send(packet: DatagramPacket) : Completable = Completable.create {
        socket.send(packet)
        it.onComplete()
    }

    fun receive(length: Int) : Maybe<DatagramPacket> = Maybe.create<DatagramPacket> {
        val buffer = ByteArray(length)
        val packet = DatagramPacket(buffer, buffer.size)
        try {
            socket.receive(packet)
            it.onSuccess(packet)
        } catch (e: SocketTimeoutException) {
            it.onComplete()
        }
    }
}
