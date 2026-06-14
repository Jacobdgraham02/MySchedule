package com.jacobdgraham.myschedule.ui.state

import com.jacobdgraham.myschedule.domain.model.MonthSchedule
import com.jacobdgraham.myschedule.domain.model.ShiftDefinition
import java.time.YearMonth

/**
 * Represents the state of the UI for the calendar
 *
 * [CalendarUiState] contains data for the currently selected month, schedule data for that month, and any basic loading/error information if available
 *
 * @property selectedMonth the year and month currently being displayed on the calendar
 * @property monthSchedule the schedule data for the selected month
 * @property isLoading whether schedule data is currently being loaded
 * @property hasAttemptedLoad whether schedule data has attempted to load yet
 * @property errorMessage an optional error message to display if for some reason loading data fails
 */
data class CalendarUiState (val selectedMonth: YearMonth = YearMonth.now(), val monthSchedule: MonthSchedule? = null,
                            val shiftDefinitions: List<ShiftDefinition> = emptyList(),
                            val isLoading: Boolean = false, val hasAttemptedLoad: Boolean = false,
                            val errorMessage: String? = null)