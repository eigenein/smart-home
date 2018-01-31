package me.eigenein.smarthome.ui

import android.view.View
import me.eigenein.smarthome.R
import me.eigenein.smarthome.core.Device

class MulticolorLightingDeviceItem(device: Device) : DeviceAdapter.Item(device) {
    override val itemViewType = R.layout.device_multicolor_lighting

    init {
        DeviceAdapter.registerViewType(itemViewType, ::MulticolorLightingViewHolder)
    }

    class MulticolorLightingViewHolder(itemView: View) : DeviceAdapter.Item.ViewHolder(itemView) {
        override fun bind(item: DeviceAdapter.Item) {
            super.bind(item)
        }
    }
}
