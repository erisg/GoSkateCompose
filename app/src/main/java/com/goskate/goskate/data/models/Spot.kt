package com.goskate.goskate.data.models

import com.goskate.goskate.domain.models.SpotType

data class Spot(
    val name: String,
    val address: String,
    val coordinates: String,
    val type: SpotType,
    val images: List<String>,
    val assignedDays: AssignedDays,
)

data class AssignedDays(
    val skatepark: String?,
    val bmx: String?,
)
