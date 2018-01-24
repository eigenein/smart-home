package me.eigenein.smarthome

import android.net.nsd.NsdManager
import android.util.Log
import io.reactivex.Flowable
import me.eigenein.smarthome.extensions.discoverServicesFlowable
import me.eigenein.smarthome.extensions.resolveServiceSingle

class DeviceManager {
    companion object {
        private const val SERVICE_TYPE = "_smart-home._udp."
        private val TAG = DeviceManager::class.java.simpleName

        fun discover(nsdManager: NsdManager): Flowable<Response> = nsdManager
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
            .map { Device(it.host, it.port) }
            .flatMap {
                it
                    // TODO: maybe no need to wait for the response right here.
                    // TODO: maybe it's better to move listening to the device.
                    .sendAndReceiveMaybe(RequestBuilder.ping())
                    .toFlowable()
                    .onErrorResumeNext { throwable: Throwable ->
                        Log.e(TAG, "Failed to ping the device: $it", throwable)
                        Flowable.empty()
                    }
            }
    }
}
