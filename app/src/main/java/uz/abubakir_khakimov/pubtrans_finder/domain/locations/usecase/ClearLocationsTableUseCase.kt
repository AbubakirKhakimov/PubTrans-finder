package uz.abubakir_khakimov.pubtrans_finder.domain.locations.usecase

import uz.abubakir_khakimov.pubtrans_finder.domain.locations.repositories.LocationsRepository
import javax.inject.Inject

class ClearLocationsTableUseCase @Inject constructor(
    private val locationsRepository: LocationsRepository
) {

    suspend fun invoke() = locationsRepository.clearLocationsTable()
}