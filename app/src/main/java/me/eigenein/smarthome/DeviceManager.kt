package me.eigenein.smarthome

import android.net.nsd.NsdManager
import io.reactivex.Flowable
import me.eigenein.smarthome.extensions.*
import org.json.JSONObject
import java.net.DatagramSocket

class DeviceManager {
    companion object {
        private const val SERVICE_TYPE = "_smart-home._udp."
        private const val SOCKET_TIMEOUT_MILLIS = 10000
        private const val DATAGRAM_LENGTH = 200

        // FIXME: should return high-level responses.
        fun discover(nsdManager: NsdManager): Flowable<JSONObject> = nsdManager
            .discoverServicesFlowable(SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD)
            .flatMap {
                nsdManager
                    .resolveServiceSingle(it)
                    .toFlowable()
                    .onErrorResumeNext(Flowable.empty())
            }
            .flatMap {
                val socket = DatagramSocket().init { soTimeout = SOCKET_TIMEOUT_MILLIS }
                socket
                    .sendCompletable(JSONObject("""{"type": "ping"}""").toDatagramPacket(it.host, it.port))
                    .andThen(socket.receiveMaybe(DATAGRAM_LENGTH))
                    .map { it.toJSONObject() } // FIXME: check response messageId?
                    .toFlowable()
                    .onErrorResumeNext(Flowable.empty())
            }
    }
}
