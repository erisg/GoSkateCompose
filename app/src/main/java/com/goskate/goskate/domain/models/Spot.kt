package com.goskate.goskate.domain.models

data class Spot(
    val name: String = String(),
    val address: String = String(),
    val latLng: Double = 0.0,
    val lonLng: Double = 0.0,
    val type: SpotType = SpotType.SKATEPARK,
    val images: List<String> = listOf(),
    val assignedDays: AssignedDays,
)

data class AssignedDays(
    val skatepark: String?,
    val bmx: String?,
)

enum class SpotType {
    SKATEPARK,
    EVENT,
    STREET,
}
