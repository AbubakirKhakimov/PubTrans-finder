package uz.abubakir_khakimov.pubtrans_finder.domain.locations.repositories

import kotlinx.coroutines.flow.Flow
import uz.abubakir_khakimov.pubtrans_finder.core.common.Result
import uz.abubakir_khakimov.pubtrans_finder.domain.locations.models.Location

interface LocationsRepository {

    fun observeLocation(): Result<Flow<Location?>>

    suspend fun addLocation(location: Location)

    suspend fun addLocations(locations: List<Location>)

    suspend fun clearLocationsTable()
}