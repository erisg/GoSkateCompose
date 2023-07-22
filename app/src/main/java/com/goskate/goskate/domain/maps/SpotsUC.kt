package com.goskate.goskate.domain.maps

import com.goskate.goskate.data.map.MapsRepository
import javax.inject.Inject

class SpotsUC @Inject constructor(
    private val mapsRepository: MapsRepository,
) {
    suspend fun getAllSpots() = mapsRepository.getAllSpots()
}
