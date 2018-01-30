package me.eigenein.smarthome.ui

import me.eigenein.smarthome.core.Device
import me.eigenein.smarthome.core.DeviceType

class ItemBuilder {
    companion object {
        private val builders = mapOf<DeviceType, (Device) -> DeviceAdapter.Item>(
            DeviceType.RGB to ::RGBItem
        )

        fun build(device: Device) = builders.getValue(device.type)(device)
    }
}
