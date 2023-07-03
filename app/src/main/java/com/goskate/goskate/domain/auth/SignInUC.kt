package com.goskate.goskate.domain.auth

import com.goskate.goskate.data.auth.AuthRepository
import javax.inject.Inject

class SignInUC @Inject constructor(private val authRepository: AuthRepository) {
    suspend fun signUp(email: String, password: String) = authRepository.signUp(email, password)
    suspend fun signIn(email: String, password: String) = authRepository.signIn(email, password)
}
