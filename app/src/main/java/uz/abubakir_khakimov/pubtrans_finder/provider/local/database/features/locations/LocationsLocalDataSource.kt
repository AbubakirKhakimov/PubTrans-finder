package uz.abubakir_khakimov.pubtrans_finder.provider.local.database.features.locations

import kotlinx.coroutines.flow.Flow
import uz.abubakir_khakimov.pubtrans_finder.core.common.Result
import uz.abubakir_khakimov.pubtrans_finder.provider.local.database.features.locations.entities.LocationLocalEntity

interface LocationsLocalDataSource {

    fun observeLocation(): Result<Flow<LocationLocalEntity?>>

    suspend fun addLocation(location: LocationLocalEntity)

    suspend fun addLocations(locations: List<LocationLocalEntity>)

    suspend fun clearLocationsTable()
}