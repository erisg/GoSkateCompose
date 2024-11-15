package com.goskate.goskate.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goskate.goskate.data.di.IoDispatcher
import com.goskate.goskate.domain.auth.AuthUC
import com.goskate.goskate.domain.models.User
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
class AuthViewModel @Inject constructor(
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher,
    private val authUC: AuthUC,
) : ViewModel() {

    private val _signUpState: MutableStateFlow<LoginState> = MutableStateFlow(LoginState())
    val signUpState: StateFlow<LoginState>
        get() = _signUpState.asStateFlow()

    private val _sigInState: MutableStateFlow<LoginState> = MutableStateFlow(LoginState())
    val signInState: StateFlow<LoginState>
        get() = _sigInState.asStateFlow()

    fun signIn(email: String, password: String) {
        viewModelScope.launch(dispatcher) {
            authUC.signIn(email, password)
                .onStart {
                    _sigInState.update { state ->
                        state.copy(isLoading = true)
                    }
                }.map { value: Result<User> ->
                    value.fold(
                        onSuccess = { data ->
                            _sigInState.update {
                                it.copy(data = data)
                            }
                        },
                        onFailure = { exception ->
                            _sigInState.update {
                                it.copy(messageError = exception.message.orEmpty())
                            }
                        },
                    )
                }
                .catch { exception ->
                    _sigInState.update {
                        it.copy(messageError = exception.message.orEmpty())
                    }
                }.flowOn(dispatcher).launchIn(viewModelScope)
        }
    }

    fun signUp(email: String, password: String, name: String, age: String) {
        viewModelScope.launch(dispatcher) {
            authUC.signUp(email, password, name, age)
                .onStart {
                    _signUpState.update { state ->
                        state.copy(isLoading = true)
                    }
                }.map { value: Result<User> ->
                    value.fold(
                        onSuccess = { data ->
                            _signUpState.update {
                                it.copy(data = data)
                            }
                        },
                        onFailure = { exception ->
                            _signUpState.update {
                                it.copy(messageError = exception.message.orEmpty())
                            }
                        },
                    )
                }
                .catch { exception ->
                    _sigInState.update {
                        it.copy(messageError = exception.message.orEmpty())
                    }
                }.flowOn(dispatcher).launchIn(viewModelScope)
        }
    }

    fun isLoggedIn() = authUC.isLoggedIn()
}
data class LoginState(
    val isLoading: Boolean = false,
    val data: User? = null,
    val messageError: String = String(),
)
