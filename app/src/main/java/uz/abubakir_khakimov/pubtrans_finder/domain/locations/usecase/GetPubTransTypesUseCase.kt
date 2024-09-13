package uz.abubakir_khakimov.pubtrans_finder.domain.locations.usecase

import uz.abubakir_khakimov.pubtrans_finder.R
import uz.abubakir_khakimov.pubtrans_finder.core.common.Result
import uz.abubakir_khakimov.pubtrans_finder.domain.locations.models.PubTransStation
import uz.abubakir_khakimov.pubtrans_finder.domain.locations.models.PubTransType
import javax.inject.Inject

class GetPubTransTypesUseCase @Inject constructor() {

    fun invoke(): Result<List<PubTransType>> = Result.success(
        data = listOf(
            PubTransType(
                name = "Bus",
                icon = R.drawable.ic_outlined_bus
            ),
            PubTransType(
                name = "Metro",
                icon = R.drawable.ic_metro
            )
        )
    )
}