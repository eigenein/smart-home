package me.eigenein.smarthome.extensions

import me.eigenein.smarthome.Response
import org.json.JSONObject

fun JSONObject.toResponse() = Response(this)
