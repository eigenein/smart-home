package me.eigenein.smarthome.core

data class Device(val uuid: String, val type: DeviceType, var address: DeviceAddress)
