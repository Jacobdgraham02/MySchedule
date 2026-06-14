package com.jacobdgraham.myschedule.data.local

import com.jacobdgraham.myschedule.domain.model.ShiftDefinition

/**
 * Converts a local Room cached [ShiftDefinitionEntity] into a domain [ShiftDefinition]
 * This is used for converstion before passing them to the ViewModel, which is then passed to the UI
 * @return useable domain model
 */
fun ShiftDefinitionEntity.toDomain(): ShiftDefinition {
    return ShiftDefinition(
        code = code,
        definition = definition
    )
}

/**
 * Converts a domain [ShiftDefinition] into a Room [ShiftDefinitionEntity]
 * This is used when reading shift definition data from Firestore and loading into local Room cache
 * @return usable Room entity
 */
fun ShiftDefinition.toEntity(): ShiftDefinitionEntity {
    return ShiftDefinitionEntity(
        code = code,
        definition = definition
    )
}