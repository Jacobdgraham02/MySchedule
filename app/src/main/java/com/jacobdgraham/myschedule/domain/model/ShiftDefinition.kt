package com.jacobdgraham.myschedule.domain.model

/**
 * Domain model describing what a shift code means. Shift code stored separate from definition so the calendar will only display compact shift values on each date
 * and legend can display human readable explanation for what a shift code present on the calendar means
 * Example:
 * ```
 * code = "0610"
 * definition = "6:00 a.m. to 4:00 p.m."
 * ```
 * Shift definitions are loaded dynamically based on the shift codes used in the selected month.
 * @property code the shift code shown inside the individual calendar cells
 * @property definition human readable explanation of the shift code
 */
data class ShiftDefinition(val code: String, val definition: String)