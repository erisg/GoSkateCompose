package com.goskate.goskate.domain.auth

import com.goskate.goskate.data.auth.AuthRepository
import javax.inject.Inject

class AuthUC @Inject constructor(private val authRepository: AuthRepository) {
    suspend fun signUp(
        email: String,
        password: String,
        name: String,
        age: String,
    ) = authRepository.signUp(email, password, name, age)
    suspend fun signIn(
        email: String,
        password: String,
    ) = authRepository.signIn(email, password)
}
