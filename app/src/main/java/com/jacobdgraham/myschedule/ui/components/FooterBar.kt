package com.jacobdgraham.myschedule.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.room.util.TableInfo

/**
 * Displays navigation actions at the bottom of the schedule screen
 *
 * [FooterBar] allows the user to move between months or return to the current month
 *
 * @param onPreviousMonth called when the button is pressed that lets you see the previous month
 * @param onToday called when the button is pressed to show today
 * @param onNextMonth called when the button is pressed to show the next month
 * @param modifier Optional modifier for styling and layout
 */
@Composable
fun FooterBar(onPreviousMonth: () -> Unit, onToday: () -> Unit, onNextMonth: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth().padding(horizontal = 28.dp, vertical = 4.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedButton(onClick = onPreviousMonth) {
            Text(text = "Previous month")
        }

        Button(onClick = onToday) {
            Text(text = "Current month")
        }

        OutlinedButton(onClick = onNextMonth) {
            Text(text = "Next month")
        }
    }
}