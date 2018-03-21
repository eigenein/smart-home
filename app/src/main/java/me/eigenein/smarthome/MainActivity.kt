package me.eigenein.smarthome

import android.content.Context
import android.net.nsd.NsdManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_layout.*
import me.eigenein.smarthome.core.DeviceManager
import me.eigenein.smarthome.core.requests.PingRequest
import me.eigenein.smarthome.extensions.addTo
import me.eigenein.smarthome.ui.DeviceAdapter

class MainActivity : AppCompatActivity() {

    private val adapter = DeviceAdapter()
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()

        DeviceManager
            .listen()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(adapter::handleResponse)
            .addTo(disposable)

        DeviceManager
            .discover(getSystemService(Context.NSD_SERVICE) as NsdManager)
            .flatMapCompletable { DeviceManager.send(it, PingRequest()) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
            .addTo(disposable)
    }

    override fun onPause() {
        disposable.clear()
        super.onPause()
    }
}
