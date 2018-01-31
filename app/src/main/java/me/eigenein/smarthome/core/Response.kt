package me.eigenein.smarthome.core

data class Response(
    val deviceAddress: DeviceAddress,
    val messageId: Int,
    val uuid: String,
    val deviceType: DeviceType,
    val status: Status
)
