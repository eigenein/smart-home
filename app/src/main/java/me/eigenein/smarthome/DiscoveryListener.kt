package me.eigenein.smarthome

import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.util.Log

open class DiscoveryListener : NsdManager.DiscoveryListener {

    override fun onServiceFound(serviceInfo: NsdServiceInfo) {
        Log.i(TAG, "Service found: $serviceInfo")
    }

    override fun onServiceLost(serviceInfo: NsdServiceInfo) {
        Log.i(TAG, "Service lost: $serviceInfo")
    }

    override fun onDiscoveryStarted(serviceType: String) {
        Log.i(TAG, "Discovery started: $serviceType")
    }

    override fun onDiscoveryStopped(serviceType: String) {
        Log.i(TAG, "Discovery stopped: $serviceType")
    }

    override fun onStartDiscoveryFailed(serviceType: String, errorCode: Int) {
        Log.e(TAG, "Discovery start failed: $serviceType")
    }

    override fun onStopDiscoveryFailed(serviceType: String, errorCode: Int) {
        Log.e(TAG, "Discovery stop failed: $serviceType")
    }

    companion object {
        private val TAG = DiscoveryListener::class.java.simpleName
    }
}

class OnServiceFoundDiscoveryListener(private val listener: (NsdServiceInfo) -> Unit) : DiscoveryListener() {
    override fun onServiceFound(serviceInfo: NsdServiceInfo) = listener(serviceInfo)
}
