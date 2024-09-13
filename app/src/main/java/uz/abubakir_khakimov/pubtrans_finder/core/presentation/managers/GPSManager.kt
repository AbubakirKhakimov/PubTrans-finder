package uz.abubakir_khakimov.pubtrans_finder.core.presentation.managers

import android.app.Activity
import android.location.LocationManager
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.tasks.Task

interface GPSManager {

    fun isGPSEnabled(): Boolean

    fun checkGPSElseEnable(activity: Activity): Task<LocationSettingsResponse>?

    fun offerToEnableGPS(activity: Activity): Task<LocationSettingsResponse>
}