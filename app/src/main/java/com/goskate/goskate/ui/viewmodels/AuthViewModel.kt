package com.goskate.goskate.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goskate.goskate.domain.auth.SignInUC
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

@HiltViewModel
class AuthViewModel @Inject constructor(
@IoDispatcher
private val dispatcher: CoroutineDispatcher,
    private val signInUC: SignInUC
    ) : ViewModel(){
    fun signIn(){
        viewModelScope.launch(dispatcher) {
    }
}
    }