package me.eigenein.smarthome

import io.reactivex.Maybe
import me.eigenein.smarthome.extensions.*
import org.json.JSONObject
import java.net.DatagramSocket
import java.net.InetAddress

data class Device(private val address: InetAddress, private val port: Int) {

    private val socket: DatagramSocket = DatagramSocket().init { soTimeout = SOCKET_TIMEOUT_MILLIS }

    fun sendAndReceiveMaybe(request: JSONObject): Maybe<Response> {
        return socket
            .sendCompletable(request.toDatagramPacket(address, port))
            .andThen(socket.receiveMaybe(DATAGRAM_LENGTH)) // TODO: map to device' responses instead and move timeout outside?
            .map { Response.from(it.toJSONObject()) } // FIXME: check response messageId?
    }

    companion object {
        private const val SOCKET_TIMEOUT_MILLIS = 5000
        private const val DATAGRAM_LENGTH = 200
    }
}
