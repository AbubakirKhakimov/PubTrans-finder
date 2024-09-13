package uz.abubakir_khakimov.pubtrans_finder.domain.locations.usecase

import uz.abubakir_khakimov.pubtrans_finder.domain.locations.models.Location
import uz.abubakir_khakimov.pubtrans_finder.domain.locations.repositories.LocationsRepository
import javax.inject.Inject

class AddLocationUseCase @Inject constructor(
    private val locationsRepository: LocationsRepository
) {

    suspend fun invoke(location: Location) = locationsRepository.addLocation(location = location)
}