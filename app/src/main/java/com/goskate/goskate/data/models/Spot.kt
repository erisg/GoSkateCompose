package com.goskate.goskate.data.models

import com.goskate.goskate.domain.models.SpotType
import com.goskate.goskate.domain.models.Spot as SpotDomain

data class Spot(
    val name: String = String(),
    val address: String = String(),
    val latLng: String = String(),
    val lonLng: String = String(),
    val type: String = String(),
) {
    fun toDomainSpot(): SpotDomain {
        return SpotDomain(
            name = name,
            address = address,
            latLng = latLng,
            lonLng = lonLng,
            type = when (type) {
                "skatepark" -> SpotType.SKATEPARK
                "event" -> SpotType.EVENT
                "street" -> SpotType.STREET
                "vertical" -> SpotType.VERTICAL
                else -> SpotType.STREET
            },
        )
    }
}

data class AssignedDays(
    val skate: String?,
    val bmx: String?,
)
