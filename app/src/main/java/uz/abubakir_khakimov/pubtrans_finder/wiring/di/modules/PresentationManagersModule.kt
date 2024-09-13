package uz.abubakir_khakimov.pubtrans_finder.wiring.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.abubakir_khakimov.pubtrans_finder.core.presentation.managers.LocationManager
import uz.abubakir_khakimov.pubtrans_finder.core.presentation.managers.LocationManagerImpl
import uz.abubakir_khakimov.pubtrans_finder.core.presentation.managers.PermissionManager
import uz.abubakir_khakimov.pubtrans_finder.core.presentation.managers.PermissionManagerImpl

@Module
@InstallIn(SingletonComponent::class)
class PresentationManagersModule {

    @Provides
    fun provideLocationManager(): LocationManager = LocationManagerImpl()

    @Provides
    fun providePermissionManager(): PermissionManager = PermissionManagerImpl()
}