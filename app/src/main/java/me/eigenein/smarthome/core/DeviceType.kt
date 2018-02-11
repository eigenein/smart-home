package me.eigenein.smarthome.core

import me.eigenein.smarthome.R

enum class DeviceType(val layoutResourceId: Int, val descriptionResourceId: Int) {
    MULTICOLOR_LIGHTING(R.layout.device_multicolor_lighting, R.string.device_type_multicolor_lighting)
}
