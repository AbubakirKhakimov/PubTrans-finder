package uz.abubakir_khakimov.pubtrans_finder.provider.local.database.features.locations.sources

import kotlinx.coroutines.flow.Flow
import uz.abubakir_khakimov.pubtrans_finder.core.common.Result
import uz.abubakir_khakimov.pubtrans_finder.provider.local.database.features.locations.LocationsLocalDataSource
import uz.abubakir_khakimov.pubtrans_finder.provider.local.database.features.locations.dao.LocationsDao
import uz.abubakir_khakimov.pubtrans_finder.provider.local.database.features.locations.entities.LocationLocalEntity
import javax.inject.Inject

class LocationsLocalDataSourceImpl @Inject constructor(
    private val locationsDao: LocationsDao
) : LocationsLocalDataSource {

    override fun observeLocation(): Result<Flow<LocationLocalEntity?>> =
        try {
            val data = locationsDao.observeLocation()
            Result.success(data)
        } catch (t: Throwable) {
            Result.error(t)
        }

    override suspend fun addLocation(location: LocationLocalEntity) =
        locationsDao.addLocation(location = location)

    override suspend fun addLocations(locations: List<LocationLocalEntity>) =
        locationsDao.addLocations(locations = locations)

    override suspend fun clearLocationsTable() =
        locationsDao.clearLocationsTable()
}