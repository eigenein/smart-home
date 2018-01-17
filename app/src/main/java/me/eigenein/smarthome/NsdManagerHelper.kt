package me.eigenein.smarthome

import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.util.Log
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Single
import java.io.IOException

class NsdManagerHelper(private val manager: NsdManager) {
    companion object {
        private val TAG = NsdManagerHelper::class.java.simpleName
    }

    fun discover(serviceType: String, protocolType: Int): Flowable<NsdServiceInfo> = Flowable.create({
        val listener = object : NsdManager.DiscoveryListener {
            override fun onServiceFound(serviceInfo: NsdServiceInfo) {
                Log.i(TAG, "Service found: $serviceInfo")
                it.onNext(serviceInfo)
            }

            override fun onServiceLost(serviceInfo: NsdServiceInfo) {
                Log.i(TAG, "Service lost: $serviceInfo")
            }

            override fun onDiscoveryStarted(serviceType: String) {
                Log.i(TAG, "Discovery started: $serviceType")
            }

            override fun onDiscoveryStopped(serviceType: String) {
                Log.i(TAG, "Discovery stopped: $serviceType")
                it.onComplete()
            }

            override fun onStartDiscoveryFailed(serviceType: String, errorCode: Int) {
                val message = "Discovery start failed: $serviceType"
                Log.e(TAG, message)
                it.onError(IOException(message))
            }

            override fun onStopDiscoveryFailed(serviceType: String, errorCode: Int) {
                val message = "Discovery stop failed: $serviceType"
                Log.e(TAG, message)
                it.onError(IOException(message))
            }
        }

        it.setCancellable { manager.stopServiceDiscovery(listener) }
        manager.discoverServices(serviceType, protocolType, listener)
    }, BackpressureStrategy.BUFFER)

    fun resolve(serviceInfo: NsdServiceInfo): Single<NsdServiceInfo> = Single.create<NsdServiceInfo> {
        val listener = object : NsdManager.ResolveListener {
            override fun onServiceResolved(serviceInfo: NsdServiceInfo) {
                Log.i(TAG, "Resolved: $serviceInfo")
                it.onSuccess(serviceInfo)
            }

            override fun onResolveFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
                Log.e(TAG, "Resolve failed: $serviceInfo")
                it.onError(IOException("resolve failed ($errorCode)"))
            }
        }
        manager.resolveService(serviceInfo, listener)
    }
}
