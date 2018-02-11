package me.eigenein.smarthome.core

import org.json.JSONObject

interface Request {
    val payload: JSONObject
}
