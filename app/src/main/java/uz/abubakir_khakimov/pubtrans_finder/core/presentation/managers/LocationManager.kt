package uz.abubakir_khakimov.pubtrans_finder.core.presentation.managers

import android.content.Context
import uz.abubakir_khakimov.pubtrans_finder.core.presentation.callback.LocationManagerCallBack

interface LocationManager {

    fun startRealtimeLocation(
        context: Context,
        locationManagerCallBack: LocationManagerCallBack
    )

    fun stopRealtimeLocation()
}