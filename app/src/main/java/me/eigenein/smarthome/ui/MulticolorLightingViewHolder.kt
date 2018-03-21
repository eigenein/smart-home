package me.eigenein.smarthome.ui

import android.view.View
import android.widget.Button
import android.widget.ImageButton
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.eigenein.smarthome.R
import me.eigenein.smarthome.core.Device
import me.eigenein.smarthome.core.DeviceManager
import me.eigenein.smarthome.core.requests.SetColorRequest
import me.eigenein.smarthome.core.requests.TurnOffRequest
import me.eigenein.smarthome.core.requests.TurnOnRequest
import me.eigenein.smarthome.core.states.MulticolorLightingState
import me.eigenein.smarthome.extensions.addTo
import me.eigenein.smarthome.extensions.showFlowable
import java.util.concurrent.TimeUnit

class MulticolorLightingViewHolder(itemView: View) : DeviceAdapter.ViewHolder(itemView) {
    private val chooseColorButton = itemView.findViewById<ImageButton>(R.id.multicolorLightingChooseColor)
    private val turnOnButton = itemView.findViewById<Button>(R.id.multicolorLightingOnButton)
    private val turnOffButton = itemView.findViewById<Button>(R.id.multicolorLightingOffButton)

    private lateinit var state: MulticolorLightingState

    init {
        chooseColorButton.setOnClickListener {
            ColorPickerDialogBuilder
                .with(itemView.context)
                .setTitle(device.state.name)
                .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                .density(12)
                .lightnessSliderOnly()
                .showFlowable(state.toColor())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .debounce(20, TimeUnit.MILLISECONDS)
                .flatMapCompletable { DeviceManager.send(device.state.address, SetColorRequest.fromColor(it)) }
                .subscribe()
                .addTo(disposable)
        }
        turnOnButton.setOnClickListener {
            DeviceManager
                .send(device.state.address, TurnOnRequest())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
                .addTo(disposable)
        }
        turnOffButton.setOnClickListener {
            DeviceManager
                .send(device.state.address, TurnOffRequest())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
                .addTo(disposable)
        }
    }

    override fun bind(device: Device) {
        super.bind(device)
        state = device.state.customState as MulticolorLightingState
    }
}
