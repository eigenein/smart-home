package me.eigenein.smarthome.ui

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import me.eigenein.smarthome.R
import me.eigenein.smarthome.core.Device
import me.eigenein.smarthome.core.Response

class DeviceAdapter : RecyclerView.Adapter<DeviceAdapter.Item.ViewHolder>() {

    private val items = mutableListOf<Item>()

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = items[position].itemViewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Item.ViewHolder {
        return viewHolders.getValue(viewType)(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.device_multicolor_lighting, parent, false)
        )
    }

    override fun onBindViewHolder(holder: Item.ViewHolder, position: Int) = holder.bind(items[position])

    fun handleResponse(response: Response) {
        val index = items.indexOfFirst { it.device.lastResponse.uuid == response.uuid }
        if (index != -1) {
            val device = items[index].device
            Log.d(TAG, "Updating existing device $device")
            device.lastResponse = response
            notifyItemChanged(index)
        } else {
            val device = Device(response)
            Log.d(TAG, "Discovered new device $device")
            items.add(DeviceItemBuilder.build(device))
            notifyItemInserted(items.size - 1)
        }
    }

    companion object {
        private val TAG = DeviceAdapter::class.java.simpleName

        private val viewHolders: MutableMap<Int, (View) -> DeviceAdapter.Item.ViewHolder> = mutableMapOf()

        fun registerViewType(viewType: Int, createViewHolder: (View) -> DeviceAdapter.Item.ViewHolder) {
            viewHolders[viewType] = createViewHolder
        }
    }

    abstract class Item(val device: Device) {
        abstract val itemViewType: Int

        abstract class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private var titleTextView = itemView.findViewById<TextView>(R.id.device_title)
            private var subtitleTextView = itemView.findViewById<TextView>(R.id.item_base_subtitle)

            open fun bind(item: Item) {
                titleTextView.text = item.device.lastResponse.uuid
                subtitleTextView.setText(item.device.lastResponse.deviceType.descriptionResourceId)
            }
        }
    }
}
