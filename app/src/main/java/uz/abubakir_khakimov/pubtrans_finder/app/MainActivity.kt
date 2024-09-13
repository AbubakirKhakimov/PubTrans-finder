package uz.abubakir_khakimov.pubtrans_finder.app

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dagger.hilt.android.AndroidEntryPoint
import uz.abubakir_khakimov.pubtrans_finder.core.presentation.callback.PermissionManagerCallBack
import uz.abubakir_khakimov.pubtrans_finder.core.presentation.extensions.isServiceRunning
import uz.abubakir_khakimov.pubtrans_finder.core.presentation.managers.PermissionManager
import uz.abubakir_khakimov.pubtrans_finder.databinding.ActivityMainBinding
import uz.abubakir_khakimov.pubtrans_finder.features.home.callbacks.LocationProviderServiceCallBack
import uz.abubakir_khakimov.pubtrans_finder.features.home.services.LocationProviderService
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), PermissionManagerCallBack, LocationProviderServiceCallBack {

    @Inject lateinit var permissionManager: PermissionManager

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        permissionManager.registerActivityResult(activity = this, callBack = this)
    }

    override fun onActivityResult(result: Map<String, Boolean>, tag: Any) {
        if (result.containsValue(value = false)) return

        if (!isServiceRunning(serviceClass = LocationProviderService::class.java))
            runLocationProviderService()
    }

    private fun runLocationProviderService() = Intent(
        /* packageContext = */ applicationContext,
        /* cls = */ LocationProviderService::class.java
    ).also { intent ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            startForegroundService(/* service = */ intent)
        else startService(/* service = */ intent)
    }

    private fun stopLocationProviderService() = Intent(
        /* packageContext = */ applicationContext,
        /* cls = */ LocationProviderService::class.java
    ).also { intent -> stopService(/* name = */ intent) }

    override fun tryRunLocationProviderService() = permissionManager.checkPermissions(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        autoAsk = true
    ).let {}

    override fun onDestroy() {
        super.onDestroy()

        stopLocationProviderService()
    }
}