package me.eigenein.smarthome.core

import android.net.nsd.NsdManager
import android.util.Log
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import me.eigenein.smarthome.extensions.*
import java.net.DatagramSocket
import java.net.SocketException

class DeviceManager {

    companion object {
        private const val DATAGRAM_SIZE = 256
        private const val SERVICE_TYPE = "_smart-home._udp."

        private val TAG = DeviceManager::class.java.simpleName

        private lateinit var socket: DatagramSocket

        fun discover(nsdManager: NsdManager): Flowable<DeviceAddress> = nsdManager
            .discoverServicesFlowable(SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD)
            .flatMap {
                nsdManager
                    .resolveServiceSingle(it)
                    .toFlowable()
                    .onErrorResumeNext { throwable: Throwable ->
                        Log.e(TAG, "Failed to resolve service: $it", throwable)
                        Flowable.empty()
                    }
            }
            .map { DeviceAddress(it.host, it.port) }

        fun listen(): Flowable<DeviceState> = Flowable.create({
            socket = DatagramSocket()
            it.setCancellable { socket.close() }
            while (true) {
                try {
                    socket.receive(DATAGRAM_SIZE).toDeviceState().emitTo(it)
                } catch (throwable: SocketException) {
                    Log.w(TAG, throwable.message)
                    it.onComplete()
                    break
                } catch (throwable: Exception) {
                    Log.e(TAG, "Failed to receive a response.", throwable)
                }
            }
        }, BackpressureStrategy.BUFFER)

        fun send(request: DeviceRequest): Completable = Completable.create {
            Log.v(TAG, "Sending $request")
            request.toDatagramPacket(request.address.address, request.address.port).sendTo(socket)
            it.onComplete()
        }

        fun send(address: DeviceAddress, request: Request) = send(DeviceRequest(address, request))
    }
}
