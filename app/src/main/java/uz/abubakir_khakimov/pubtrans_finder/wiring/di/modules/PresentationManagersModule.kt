package uz.abubakir_khakimov.pubtrans_finder.wiring.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.abubakir_khakimov.pubtrans_finder.core.presentation.managers.GPSManager
import uz.abubakir_khakimov.pubtrans_finder.core.presentation.managers.GPSManagerImpl
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

    @Provides
    fun provideGPSManager(
        @ApplicationContext applicationContext: Context
    ): GPSManager = GPSManagerImpl(applicationContext = applicationContext)
}