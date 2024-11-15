package com.goskate.goskate.data.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.goskate.goskate.data.auth.AuthRepository
import com.goskate.goskate.data.auth.AuthRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    fun provideAuthRepository(
        auth: FirebaseAuth,
        database: FirebaseDatabase,
    ): AuthRepository {
        return AuthRepositoryImpl(auth, database)
    }
}
