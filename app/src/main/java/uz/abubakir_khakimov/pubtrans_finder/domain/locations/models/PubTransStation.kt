package uz.abubakir_khakimov.pubtrans_finder.domain.locations.models

data class PubTransStation(
    val name: String,
    val walkTime: String,
    val distance: String,
    val transportNumber: String,
    val direction: String,
    val times: String
)
