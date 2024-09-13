package uz.abubakir_khakimov.pubtrans_finder.features.home.fragments

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
import android.view.Window
import android.view.WindowInsetsController
import android.widget.Spinner
import androidx.annotation.ColorInt
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import uz.abubakir_khakimov.pubtrans_finder.R
import uz.abubakir_khakimov.pubtrans_finder.core.presentation.extensions.collectWithLifeCircle
import uz.abubakir_khakimov.pubtrans_finder.core.presentation.utils.WindowTools
import uz.abubakir_khakimov.pubtrans_finder.databinding.FragmentHomeBinding
import uz.abubakir_khakimov.pubtrans_finder.domain.locations.models.Location
import uz.abubakir_khakimov.pubtrans_finder.features.home.adapters.DistancesAdapter
import uz.abubakir_khakimov.pubtrans_finder.features.home.adapters.PubTransStationsAdapter
import uz.abubakir_khakimov.pubtrans_finder.features.home.adapters.PubTransTypesAdapter
import uz.abubakir_khakimov.pubtrans_finder.features.home.extensions.tryRunLocationProviderService
import uz.abubakir_khakimov.pubtrans_finder.features.home.viewmodels.HomeViewModel


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), OnMapReadyCallback {

    private val binding by viewBinding (FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels()
    private val pubTransStationsAdapter: PubTransStationsAdapter by lazy { PubTransStationsAdapter() }
    private val pubTransTypesAdapter: PubTransTypesAdapter by lazy {
        PubTransTypesAdapter(context = requireContext())
    }
    private val distancesAdapter: DistancesAdapter by lazy {
        DistancesAdapter(context = requireContext())
    }

    private var googleMap: GoogleMap? = null
    private var marker: Marker? = null
    private var locationAutoMove = true

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        tryRunLocationProviderService()
        viewModel.observeLocation()

        googleMap.setOnCameraMoveStartedListener { i ->
            if (i == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE && locationAutoMove)
                locationAutoMove = false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMapCallBack()

        binding.updateUi()
        binding.attachListeners()

        flowCollectManager()
    }

    override fun onStart() {
        super.onStart()

        WindowTools.setDecorFitsSystemWindows(
            window = requireActivity().window,
            decorFitsSystemWindows = false
        )
        WindowTools.setSystemUiLightStatusBar(
            window = requireActivity().window,
            isLightStatusBar = true
        )
    }

    private fun initMapCallBack() {
        val mapFragment = childFragmentManager.findFragmentById(
            /* id = */ R.id.map
        ) as SupportMapFragment?
        mapFragment?.getMapAsync(/* callback = */ this)
    }

    private fun FragmentHomeBinding.updateUi(){
        bottomSheet.pubTransStationsRV.adapter = pubTransStationsAdapter
        bottomSheet.pubTransTypeSpinner.adapter = pubTransTypesAdapter
        bottomSheet.distanceSpinner.adapter = distancesAdapter
    }

    private fun FragmentHomeBinding.attachListeners(){

        goCurrentLocationButton.setOnClickListener {
            locationAutoMove = true
            changeCameraPosition(
                location = viewModel.observeLocation.replayCache.lastOrNull()
                    ?: return@setOnClickListener
            )
        }
    }

    private fun flowCollectManager(){
        observeLocationCollect()
        getPubTransStationsCollect()
        getPubTransTypesCollect()
        getDistancesCollect()
    }

    private fun observeLocationCollect() = viewModel.observeLocation
        .onEach { location ->
            changeMarkerPosition(location)
        }.collectWithLifeCircle(lifecycleOwner = viewLifecycleOwner)

    private fun getPubTransStationsCollect() = viewModel.getPubTransStations
        .onEach { pubTransStations ->
            pubTransStationsAdapter.submitList(/* list = */ pubTransStations)
        }.collectWithLifeCircle(lifecycleOwner = viewLifecycleOwner)

    private fun getPubTransTypesCollect() = viewModel.getPubTransTypes
        .onEach { pubTransTypes ->
            pubTransTypesAdapter.submitList(list = pubTransTypes)
            if (pubTransTypes.isNotEmpty())
                binding.bottomSheet.pubTransTypeSpinner.setSelection(/* position = */ 0)
        }.collectWithLifeCircle(lifecycleOwner = viewLifecycleOwner)

    private fun getDistancesCollect() = viewModel.getDistances
        .onEach { distances ->
            distancesAdapter.submitList(list = distances)
            if (distances.isNotEmpty())
                binding.bottomSheet.distanceSpinner.setSelection(/* position = */ 0)
        }.collectWithLifeCircle(lifecycleOwner = viewLifecycleOwner)

    @SuppressLint("MissingPermission")
    private fun changeMarkerPosition(location: Location){
        val latLng = LatLng(location.latitude, location.longitude)

        if (marker == null) {
            marker = googleMap?.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.defaultMarker())
            )
        }else{
            marker!!.position = latLng
        }

        changeCameraPosition(location)
    }

    private fun changeCameraPosition(location: Location) = if (locationAutoMove) {
        val cameraPosition =
            CameraPosition.Builder().target(LatLng(location.latitude, location.longitude))
                .zoom(18f)
                .build()
        googleMap?.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    } else Unit

    override fun onDestroyView() {
        super.onDestroyView()

        googleMap = null
        marker = null
    }
}