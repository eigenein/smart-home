package me.eigenein.smarthome.core

import org.json.JSONObject

abstract class Request(val address: DeviceAddress) {
    abstract val payload: JSONObject
}
