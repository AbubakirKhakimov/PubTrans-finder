package uz.abubakir_khakimov.pubtrans_finder.features.home.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import uz.abubakir_khakimov.pubtrans_finder.core.common.Result
import uz.abubakir_khakimov.pubtrans_finder.core.common.Result.Companion.asyncSuccess
import uz.abubakir_khakimov.pubtrans_finder.core.common.Result.Companion.error
import uz.abubakir_khakimov.pubtrans_finder.domain.locations.models.Location
import uz.abubakir_khakimov.pubtrans_finder.domain.locations.models.PubTransStation
import uz.abubakir_khakimov.pubtrans_finder.domain.locations.models.PubTransType
import uz.abubakir_khakimov.pubtrans_finder.domain.locations.usecase.GetDistancesUseCase
import uz.abubakir_khakimov.pubtrans_finder.domain.locations.usecase.GetPubTransStationsUseCase
import uz.abubakir_khakimov.pubtrans_finder.domain.locations.usecase.GetPubTransTypesUseCase
import uz.abubakir_khakimov.pubtrans_finder.domain.locations.usecase.ObserveLocationUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val observeLocationUseCase: ObserveLocationUseCase,
    private val getPubTransStationsUseCase: GetPubTransStationsUseCase,
    private val getPubTransTypesUseCase: GetPubTransTypesUseCase,
    private val getDistancesUseCase: GetDistancesUseCase
): ViewModel() {

    private val _observeLocation = MutableSharedFlow<Location>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val observeLocation: SharedFlow<Location> = _observeLocation.asSharedFlow()

    private val _getPubTransStations = MutableSharedFlow<List<PubTransStation>>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val getPubTransStations: SharedFlow<List<PubTransStation>> = _getPubTransStations.asSharedFlow()

    private val _getPubTransTypes = MutableSharedFlow<List<PubTransType>>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val getPubTransTypes: SharedFlow<List<PubTransType>> = _getPubTransTypes.asSharedFlow()

    private val _getDistances = MutableSharedFlow<List<String>>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val getDistances: SharedFlow<List<String>> = _getDistances.asSharedFlow()

    private var isLocationObserved: Boolean = false

    init {

        getPubTransStations()
        getPubTransTypes()
        getDistances()
    }

    fun observeLocation() {
        if (isLocationObserved) return

        isLocationObserved = true
        observeLocationUseCase.invoke().analiseObserveLocation()
    }

    private fun Result<Flow<Location?>>.analiseObserveLocation() = viewModelScope.launch{
        asyncSuccess { data, _ ->
            data?.collect { location ->
                if (location != null) _observeLocation.emit(value = location)
            }
        }
        error { error, _ ->
            isLocationObserved = false
            error?.printStackTrace()
        }
    }

    fun getPubTransStations() {
        getPubTransStationsUseCase.invoke().analiseGetPubTransStations()
    }

    private fun Result<List<PubTransStation>>.analiseGetPubTransStations() = viewModelScope.launch{
        asyncSuccess { data, _ ->
            _getPubTransStations.emit(value = data ?: return@asyncSuccess)
        }
        error { error, _ ->
            error?.printStackTrace()
        }
    }

    fun getPubTransTypes() {
        getPubTransTypesUseCase.invoke().analiseGetPubTransTypes()
    }

    private fun Result<List<PubTransType>>.analiseGetPubTransTypes() = viewModelScope.launch{
        asyncSuccess { data, _ ->
            _getPubTransTypes.emit(value = data ?: return@asyncSuccess)
        }
        error { error, _ ->
            error?.printStackTrace()
        }
    }

    fun getDistances() {
        getDistancesUseCase.invoke().analiseGetDistances()
    }

    private fun Result<List<String>>.analiseGetDistances() = viewModelScope.launch{
        asyncSuccess { data, _ ->
            _getDistances.emit(value = data ?: return@asyncSuccess)
        }
        error { error, _ ->
            error?.printStackTrace()
        }
    }
}