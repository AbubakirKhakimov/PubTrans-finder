package uz.abubakir_khakimov.pubtrans_finder.provider.data.features.locations.di.bind

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.abubakir_khakimov.pubtrans_finder.domain.locations.repositories.LocationsRepository
import uz.abubakir_khakimov.pubtrans_finder.provider.data.features.locations.repositories.LocationsRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface BindLocationsRepositories {

    @Binds
    @Singleton
    fun bindLocationsRepository(
        locationsRepositoryImpl: LocationsRepositoryImpl
    ): LocationsRepository
}