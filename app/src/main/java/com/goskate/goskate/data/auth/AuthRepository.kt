package com.goskate.goskate.data.auth

import com.goskate.goskate.domain.models.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signUp(email: String, password: String, name: String, age: String): Flow<Result<User>>
    suspend fun signIn(email: String, password: String): Flow<Result<User>>

    fun isLoggedIn(): Boolean
}
