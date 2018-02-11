package me.eigenein.smarthome.extensions

import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun Completable.applySchedulers(): Completable = this
    .subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())

fun Completable.applySchedulersAndSubscribe(disposable: CompositeDisposable): Disposable = this
    .applySchedulers()
    .subscribe()
    .addTo(disposable)
