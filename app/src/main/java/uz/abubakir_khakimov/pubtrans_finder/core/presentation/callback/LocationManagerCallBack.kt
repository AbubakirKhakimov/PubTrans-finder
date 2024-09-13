package uz.abubakir_khakimov.pubtrans_finder.core.presentation.callback

import android.location.Location

interface LocationManagerCallBack{

    fun locationChanged(newLocation: Location?)
}