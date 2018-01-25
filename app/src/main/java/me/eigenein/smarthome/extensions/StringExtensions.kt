package me.eigenein.smarthome.extensions

import org.json.JSONObject

fun String.toJSONObject() = JSONObject(this)
