package com.jacobdgraham.myschedule.ui.theme

import androidx.compose.ui.graphics.Color

val ShiftBlueColour = Color.Blue
val ShiftMagentaColour = Color.Magenta
val ShiftOrangeColour = Color(0xFFFFA500)

/**
 * Returns a colour to use when colouring text depending on the input shift code
 *
 * @param ShiftCode a given shift code, defined as xxyy, where xx is start time in 24 hour format, yy is length of shift
 * */
fun getShiftCodeColour(shiftCode: String): Color {
    return when (shiftCode) {
        "2010" -> ShiftMagentaColour
        "1510" -> ShiftOrangeColour
        "0610" -> ShiftBlueColour
        else -> Color.Unspecified
    }
}