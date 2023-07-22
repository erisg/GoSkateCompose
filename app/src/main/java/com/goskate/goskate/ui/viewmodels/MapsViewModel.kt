package com.goskate.goskate.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goskate.goskate.data.di.IoDispatcher
import com.goskate.goskate.domain.maps.SpotsUC
import com.goskate.goskate.domain.models.Spot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher,
    private val spotUC: SpotsUC,
) : ViewModel() {

    private val _spotsState: MutableStateFlow<SpotsState> = MutableStateFlow(SpotsState())
    val spotsState: StateFlow<SpotsState>
        get() = _spotsState.asStateFlow()

    fun getAllSpots() {
        viewModelScope.launch(dispatcher) {
            spotUC.getAllSpots()
                .onStart {
                    _spotsState.update { state ->
                        state.copy(isLoading = true)
                    }
                }.map { value: Result<List<Spot>> ->
                    value.fold(
                        onSuccess = { data ->
                            _spotsState.update {
                                it.copy(data = data)
                            }
                        },
                        onFailure = { exception ->
                            _spotsState.update {
                                it.copy(messageError = exception.message.orEmpty())
                            }
                        },
                    )
                }
                .catch { exception ->
                    _spotsState.update {
                        it.copy(messageError = exception.message.orEmpty())
                    }
                }.flowOn(dispatcher).launchIn(viewModelScope)
        }
    }
}
data class SpotsState(
    val isLoading: Boolean = false,
    val data: List<Spot> = emptyList(),
    val messageError: String = String(),
)
