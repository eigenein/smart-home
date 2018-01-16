package me.eigenein.smarthome

import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.util.Log
import io.reactivex.Single
import java.io.IOException

class NsdManagerHelper(val manager: NsdManager) {
    companion object {
        private val TAG = NsdManagerHelper::class.java.simpleName
    }

    fun resolve(serviceInfo: NsdServiceInfo) = Single.create<NsdServiceInfo> {
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
