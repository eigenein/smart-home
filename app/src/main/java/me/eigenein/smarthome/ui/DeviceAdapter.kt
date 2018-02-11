package me.eigenein.smarthome.ui

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.reactivex.disposables.CompositeDisposable
import me.eigenein.smarthome.R
import me.eigenein.smarthome.core.Device
import me.eigenein.smarthome.core.DeviceState
import me.eigenein.smarthome.core.DeviceType

class DeviceAdapter : RecyclerView.Adapter<DeviceAdapter.ViewHolder>() {

    private val devices = mutableListOf<Device>()

    override fun getItemCount() = devices.size

    override fun getItemViewType(position: Int) = devices[position].state.deviceType.layoutResourceId

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return viewHolders.getValue(viewType)(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.device_multicolor_lighting, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(devices[position])

    override fun onViewDetachedFromWindow(holder: ViewHolder) = holder.dispose()

    fun handleResponse(state: DeviceState) {
        Log.v(TAG, "Received $state")
        val index = devices.indexOfFirst { it.state.uuid == state.uuid }
        if (index != -1) {
            val device = devices[index]
            Log.v(TAG, "Updating existing device $device")
            device.state = state
            notifyItemChanged(index, Unit) // payload is passed to prevent the view holder disposal
        } else {
            val device = Device(state)
            Log.i(TAG, "Discovered new device $device")
            devices.add(device)
            notifyItemInserted(devices.size - 1)
        }
    }

    companion object {
        private val TAG = DeviceAdapter::class.java.simpleName

        private val viewHolders: MutableMap<Int, (View) -> DeviceAdapter.ViewHolder> = mutableMapOf(
            DeviceType.MULTICOLOR_LIGHTING.layoutResourceId to ::MulticolorLightingViewHolder
        )
    }

    abstract class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        protected val disposable = CompositeDisposable()

        protected lateinit var device: Device

        private var titleTextView = itemView.findViewById<TextView>(R.id.multicolorLightingTitle)
        private var subtitleTextView = itemView.findViewById<TextView>(R.id.multicolorLightingSubtitle)

        open fun bind(device: Device) {
            this.device = device
            titleTextView.text = device.state.name
            subtitleTextView.setText(device.state.deviceType.descriptionResourceId)
        }

        fun dispose() = disposable.clear()
    }
}
