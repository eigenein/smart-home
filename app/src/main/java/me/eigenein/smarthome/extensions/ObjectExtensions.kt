package me.eigenein.smarthome.extensions

import io.reactivex.Emitter

fun<T> T.init(init: T.() -> Unit): T {
    init()
    return this
}

fun<T> T.emitTo(emitter: Emitter<T>) = emitter.onNext(this)
