package me.eigenein.smarthome.ui

import me.eigenein.smarthome.core.Device
import me.eigenein.smarthome.core.DeviceType

class DeviceItemBuilder {
    companion object {
        private val builders = mapOf<DeviceType, (Device) -> DeviceAdapter.Item>(
            DeviceType.MulticolorLighting to ::MulticolorLightingDeviceItem
        )

        fun build(device: Device) = builders.getValue(device.lastResponse.deviceType)(device)
    }
}
