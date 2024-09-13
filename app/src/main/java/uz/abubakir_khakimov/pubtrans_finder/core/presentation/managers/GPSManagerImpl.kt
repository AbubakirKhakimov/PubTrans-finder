package uz.abubakir_khakimov.pubtrans_finder.core.presentation.managers

import android.app.Activity
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.IntentSender.SendIntentException
import android.location.LocationManager
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.Task

class GPSManagerImpl(
    private val applicationContext: Context
): GPSManager {

    private val locationManager = applicationContext.getSystemService(
        /* name = */ LOCATION_SERVICE
    ) as LocationManager

    override fun isGPSEnabled(): Boolean = locationManager.isProviderEnabled(
        /* provider = */ LocationManager.GPS_PROVIDER
    )

    override fun checkGPSElseEnable(activity: Activity): Task<LocationSettingsResponse>? =
        if (!isGPSEnabled()) offerToEnableGPS(activity = activity) else null

    override fun offerToEnableGPS(activity: Activity): Task<LocationSettingsResponse> {
        val locationRequest = LocationRequest.Builder(
            /* priority = */ Priority.PRIORITY_HIGH_ACCURACY,
            /* intervalMillis = */ LocationManagerImpl.LOCATION_REQUEST_INTERVAL
        ).build()
        val locationSettingsRequestBuilder = LocationSettingsRequest.Builder()

        locationSettingsRequestBuilder.addLocationRequest(/* request = */ locationRequest)
        locationSettingsRequestBuilder.setAlwaysShow(/* show = */ true)

        val settingsClient = LocationServices.getSettingsClient(/* activity = */ activity)
        val task = settingsClient.checkLocationSettings(/* p0 = */ locationSettingsRequestBuilder.build())

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) try {
                exception.startResolutionForResult(
                    /* activity = */ activity,
                    /* requestCode = */ REQUEST_CHECK_SETTINGS
                )
            } catch (sendIntentException: SendIntentException) {
                sendIntentException.printStackTrace()
            }
        }

        return task
    }

    companion object {

        private const val REQUEST_CHECK_SETTINGS = 0x1
    }
}