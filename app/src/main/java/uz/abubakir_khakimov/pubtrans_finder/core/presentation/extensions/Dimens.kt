package uz.abubakir_khakimov.pubtrans_finder.core.presentation.extensions

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue

fun Float.toDip(context: Context): Float = TypedValue.applyDimension(
    /* unit = */ TypedValue.COMPLEX_UNIT_DIP,
    /* value = */ this,
    /* metrics = */ context.resources.displayMetrics
)

val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Int.pxF: Float
    get() = this * Resources.getSystem().displayMetrics.density