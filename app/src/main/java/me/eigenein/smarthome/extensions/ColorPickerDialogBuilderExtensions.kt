package me.eigenein.smarthome.extensions

import android.graphics.Color
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import me.eigenein.smarthome.R

fun ColorPickerDialogBuilder.showFlowable(initialColor: Color): Flowable<Color> = Flowable.create<Color>({ emitter ->
    val dialog = this
        .initialColor(initialColor.toArgb())
        .setOnColorChangedListener { emitter.onNext(Color.valueOf(it)) }
        .setPositiveButton(R.string.ok, { _, _, _ -> emitter.onComplete() })
        .setNegativeButton(R.string.cancel, { _, _ ->
            emitter.onNext(initialColor)
            emitter.onComplete()
        })
        .build()
    emitter.setCancellable { dialog.hide() }
    dialog.show()
}, BackpressureStrategy.DROP)
