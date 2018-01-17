package me.eigenein.smarthome.extensions

fun<T> T.init(init: T.() -> Unit): T {
    init()
    return this
}
