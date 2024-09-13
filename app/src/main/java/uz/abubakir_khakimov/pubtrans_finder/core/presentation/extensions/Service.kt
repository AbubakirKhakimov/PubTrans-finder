package uz.abubakir_khakimov.pubtrans_finder.core.presentation.extensions

import android.app.ActivityManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun Fragment.isServiceRunning(serviceClass: Class<*>): Boolean =
    requireContext().isServiceRunning(serviceClass = serviceClass)

@Suppress("DEPRECATION")
fun Context.isServiceRunning(serviceClass: Class<*>): Boolean {
    val manager = getSystemService(
        /* name = */ AppCompatActivity.ACTIVITY_SERVICE
    ) as ActivityManager

    manager.getRunningServices(/* maxNum = */ Int.MAX_VALUE).forEach { service ->
        if (serviceClass.name == service.service.className) return true
    }

    return false
}