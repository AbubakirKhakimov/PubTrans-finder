package uz.abubakir_khakimov.pubtrans_finder.features.home.extensions

import androidx.fragment.app.Fragment
import uz.abubakir_khakimov.pubtrans_finder.features.home.callbacks.LocationProviderServiceCallBack

fun Fragment.tryRunLocationProviderService() = (requireActivity() as LocationProviderServiceCallBack)
    .tryRunLocationProviderService()