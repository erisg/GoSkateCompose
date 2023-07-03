package com.goskate.goskate.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goskate.goskate.data.di.IoDispatcher
import com.goskate.goskate.domain.auth.SignInUC
import com.goskate.goskate.domain.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher,
    private val signInUC: SignInUC,
) : ViewModel() {

    private val _cardsState: MutableStateFlow<LoginState> = MutableStateFlow(LoginState())
    val signInState: StateFlow<LoginState>
        get() = _cardsState.asStateFlow()

    fun signIn(email: String, password: String) {
        viewModelScope.launch(dispatcher) {
            signInUC.signIn(email, password)
        }
    }
}
data class LoginState(
    val isLoading: Boolean = false,
    val data: List<User>? = null,
    val messageError: String = String(),
)
