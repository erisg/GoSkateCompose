package com.goskate.goskate.domain.models

data class Spot(
    val name: String = String(),
    val address: String = String(),
    val latLng: String = String(),
    val lonLng: String = String(),
    val type: SpotType = SpotType.SKATEPARK,
)
data class Images(
    val url: String = String(),
)

data class AssignedDays(
    val skate: String? = null,
    val bmx: String? = null,
)

enum class SpotType {
    SKATEPARK,
    EVENT,
    STREET,
    VERTICAL
}

