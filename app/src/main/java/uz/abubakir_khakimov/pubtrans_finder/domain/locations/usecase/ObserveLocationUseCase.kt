package uz.abubakir_khakimov.pubtrans_finder.domain.locations.usecase

import kotlinx.coroutines.flow.Flow
import uz.abubakir_khakimov.pubtrans_finder.core.common.Result
import uz.abubakir_khakimov.pubtrans_finder.domain.locations.models.Location
import uz.abubakir_khakimov.pubtrans_finder.domain.locations.repositories.LocationsRepository
import javax.inject.Inject

class ObserveLocationUseCase @Inject constructor(
    private val locationsRepository: LocationsRepository
) {

    fun invoke(): Result<Flow<Location?>> = locationsRepository.observeLocation()
}