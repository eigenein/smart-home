package me.eigenein.smarthome.extensions

import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun<T> Flowable<T>.applySchedulers(): Flowable<T> = this
    .subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())

fun<T> Flowable<T>.applySchedulersAndSubscribe(onNext: (T) -> Unit, disposable: CompositeDisposable): Disposable = this
    .applySchedulers()
    .subscribe(onNext)
    .addTo(disposable)
