package me.eigenein.smarthome.ui

import android.view.View
import me.eigenein.smarthome.R
import me.eigenein.smarthome.core.Device

class RGBItem(device: Device) : DeviceAdapter.Item(device) {
    override val itemViewType = R.layout.item_rgb

    init {
        DeviceAdapter.registerViewType(itemViewType, ::LEDViewHolder)
    }

    class LEDViewHolder(itemView: View) : DeviceAdapter.Item.ViewHolder(itemView) {
        override fun bind(item: DeviceAdapter.Item) {
            // TODO
        }
    }
}
