package com.jacobdgraham.myschedule.data.local

import com.jacobdgraham.myschedule.domain.model.ShiftDefinition

fun ShiftDefinitionEntity.toDomain(): ShiftDefinition {
    return ShiftDefinition(
        code = code,
        definition = definition
    )
}

fun ShiftDefinition.toEntity(): ShiftDefinitionEntity {
    return ShiftDefinitionEntity(
        code = code,
        definition = definition
    )
}