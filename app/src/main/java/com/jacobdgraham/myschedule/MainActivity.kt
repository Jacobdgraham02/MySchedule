package com.jacobdgraham.myschedule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.jacobdgraham.myschedule.ui.screen.CalendarScreen
import com.jacobdgraham.myschedule.ui.screen.TestCalendarScreen
import com.jacobdgraham.myschedule.ui.theme.MyScheduleTheme

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyScheduleTheme {
                TestCalendarScreen()
            }
        }
    }
}