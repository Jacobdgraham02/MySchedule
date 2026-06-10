package com.jacobdgraham.myschedule.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Placeholder for footer bar
 *
 * [FooterBar] is currently blank
 *
 * @param modifier Optional modifier for styling and layout
 */
@Composable
fun FooterBar(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth().padding(horizontal = 28.dp, vertical = 4.dp), horizontalAlignment = Alignment.CenterHorizontally) {}
}