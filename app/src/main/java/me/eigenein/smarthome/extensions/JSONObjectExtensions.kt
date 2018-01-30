package me.eigenein.smarthome.extensions

import me.eigenein.smarthome.core.Response
import org.json.JSONObject

fun JSONObject.toResponse() = Response(this)
