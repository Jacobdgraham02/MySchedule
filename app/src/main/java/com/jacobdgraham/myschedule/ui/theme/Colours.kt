package com.jacobdgraham.myschedule.ui.theme

import androidx.compose.ui.graphics.Color

val ShiftBlueColour = Color(0xFF2563EB)      // rich and calm blue
val ShiftMagentaColour = Color(0xFFC026D3)   // soft magenta/purple
val ShiftOrangeColour = Color(0xFFF97316)    // modern orange

val ShiftTealColour = Color(0xFF0F766E)      // complements blue and orange well
val ShiftAmberColour = Color(0xFFD97706)     // close to orange, but dark
val ShiftVioletColour = Color(0xFF7C3AED)    // complements magenta without being contributing to neon colour

/**
 * Returns a colour to use when colouring text depending on the input shift code
 *
 * @param shiftCode a given shift code, defined as xxyy, where xx is start time in 24 hour format, yy is length of shift
 * */
fun getShiftCodeColour(shiftCode: String): Color {
    return when (shiftCode) {
        "2010" -> ShiftMagentaColour
        "2010.25" -> ShiftVioletColour
        "1510" -> ShiftOrangeColour
        "0808" -> ShiftAmberColour
        "0610" -> ShiftBlueColour
        "0610.25" -> ShiftTealColour
        else -> Color.Unspecified
    }
}