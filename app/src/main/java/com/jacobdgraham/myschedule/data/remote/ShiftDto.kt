package com.jacobdgraham.myschedule.data.remote

/**
 * Simple data object that contains data read in directly from Firebase. Needs default constructor
 * when mapping from firebase to application
 */
data class ShiftDto (
    val year: Int = 0,
    val month: Int = 0,
    val days: Map<String, String> = emptyMap()
)