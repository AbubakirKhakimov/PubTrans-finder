package uz.abubakir_khakimov.pubtrans_finder.domain.locations.models

data class Location(
    val id: Long,
    val latitude: Double,
    val longitude: Double,
    val bearing: Float,
    val time: Long
)