package uz.abubakir_khakimov.pubtrans_finder.domain.locations.usecase

import uz.abubakir_khakimov.pubtrans_finder.core.common.Result
import uz.abubakir_khakimov.pubtrans_finder.domain.locations.models.PubTransStation
import javax.inject.Inject

class GetPubTransStationsUseCase @Inject constructor() {

    fun invoke(): Result<List<PubTransStation>> = Result.success(
        data = listOf(
            PubTransStation(
                name = "Kallilama bus stop",
                walkTime = "2 min",
                distance = "1 Km",
                transportNumber = "bus 88",
                direction = "to Kaliangkrik",
                times = "2, 12, 24 min"
            ),
            PubTransStation(
                name = "Semangga bus stop",
                walkTime = "5 min",
                distance = "2 Km",
                transportNumber = "bus 23",
                direction = "to Jatidiri",
                times = "5, 15, 23 min"
            ),
            PubTransStation(
                name = "Manggatiga bus stop",
                walkTime = "8 min",
                distance = "2.5 Km",
                transportNumber = "bus 24",
                direction = "to Kirgili",
                times = "6, 16, 24 min"
            ),
            PubTransStation(
                name = "Alisher Navoiy bus stop",
                walkTime = "10 min",
                distance = "3 Km",
                transportNumber = "bus 25",
                direction = "to Tashkent city",
                times = "5, 17, 28 min"
            )
        )
    )
}