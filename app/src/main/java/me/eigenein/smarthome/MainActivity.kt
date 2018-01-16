package me.eigenein.smarthome

import android.content.Context
import android.net.nsd.NsdManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    companion object {
        private val TAG = MainActivity::class.java.simpleName
        private const val SERVICE_TYPE = "_smart-home._udp."
        private const val SOCKET_TIMEOUT_MILLIS = 10000
    }

    private lateinit var nsdManager: NsdManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun onResume() {
        super.onResume()

        nsdManager = getSystemService(Context.NSD_SERVICE) as NsdManager
        nsdManager.discoverServices(SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, discoveryListener)
    }

    override fun onPause() {
        nsdManager.stopServiceDiscovery(discoveryListener)
        super.onPause()
    }

    private val discoveryListener = OnServiceFoundDiscoveryListener {
        Log.i(TAG, "Service found: $it")
        val helper = DatagramSocketHelper({ soTimeout = SOCKET_TIMEOUT_MILLIS })
        NsdManagerHelper(nsdManager)
            .resolve(it)
            .flatMapCompletable({ helper.send(JSONObject("""{"type": "ping"}""").toDatagramPacket(it.host, it.port)) })
            .andThen(helper.receive(200))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                // TODO
                Log.i(TAG, "Response: ${it.toJSONObject()}")
            }, {
                // TODO
                Log.e(TAG, "Datagram receive error.", it)
            }, {
                // TODO
                Log.w(TAG, "Response is not received.")
            })
    }
}
