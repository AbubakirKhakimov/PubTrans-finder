package uz.abubakir_khakimov.pubtrans_finder.domain.locations.models

import androidx.annotation.DrawableRes

data class PubTransType(
    val name: String,
    @DrawableRes val icon: Int
)
