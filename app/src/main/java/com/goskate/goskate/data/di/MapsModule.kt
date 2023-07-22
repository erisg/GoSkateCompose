package com.goskate.goskate.data.di

import com.google.firebase.database.FirebaseDatabase
import com.goskate.goskate.data.map.MapsRepository
import com.goskate.goskate.data.map.MapsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MapsModule {
    @Provides
    fun provideMapsRepository(
        database: FirebaseDatabase,
    ): MapsRepository {
        return MapsRepositoryImpl(database)
    }
}
