package uz.abubakir_khakimov.pubtrans_finder.domain.locations.usecase

import uz.abubakir_khakimov.pubtrans_finder.core.common.Result
import uz.abubakir_khakimov.pubtrans_finder.domain.locations.models.PubTransStation
import javax.inject.Inject

class GetDistancesUseCase @Inject constructor() {

    fun invoke(): Result<List<String>> = Result.success(
        data = listOf(
            "75 M",
            "80 M",
            "90 M"
        )
    )
}