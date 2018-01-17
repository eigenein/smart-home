package me.eigenein.smarthome

import android.content.Context
import android.net.nsd.NsdManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import me.eigenein.smarthome.extensions.addTo
import me.eigenein.smarthome.extensions.toJSONObject

class MainActivity : AppCompatActivity() {
    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun onResume() {
        super.onResume()

        DeviceManager
            .discover(getSystemService(Context.NSD_SERVICE) as NsdManager)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                Log.i(TAG, "Response: ${it.toJSONObject()}")
                Toast.makeText(this, it.toJSONObject().toString(), Toast.LENGTH_LONG).show()
            }, {
                Log.e(TAG, "Device discovery error.", it)
            })
            .addTo(disposable)
    }

    override fun onPause() {
        disposable.clear()
        super.onPause()
    }
}
