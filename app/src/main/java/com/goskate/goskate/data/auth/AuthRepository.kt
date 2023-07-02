package com.goskate.goskate.data.auth

import com.goskate.goskate.domain.models.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signUp(email: String, password: String): Flow<Result<String>>
    suspend fun signIn(email: String, password: String): Flow<Result<User>>
}
