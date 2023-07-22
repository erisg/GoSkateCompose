package com.goskate.goskate.data.map

import com.goskate.goskate.domain.models.Spot
import kotlinx.coroutines.flow.Flow

interface MapsRepository {
    suspend fun getAllSpots(): Flow<Result<List<Spot>>>
}
