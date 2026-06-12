package com.jacobdgraham.myschedule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.google.firebase.firestore.FirebaseFirestore
import com.jacobdgraham.myschedule.data.local.AppDatabase
import com.jacobdgraham.myschedule.data.remote.FirestoreShiftDataSource
import com.jacobdgraham.myschedule.data.repository.ShiftRepository
import com.jacobdgraham.myschedule.ui.screen.CalendarRoute
import com.jacobdgraham.myschedule.ui.screen.CalendarScreen
import com.jacobdgraham.myschedule.ui.screen.TestCalendarScreen
import com.jacobdgraham.myschedule.ui.theme.MyScheduleTheme
import com.jacobdgraham.myschedule.viewmodel.CalendarViewModel
import com.jacobdgraham.myschedule.viewmodel.CalendarViewModelFactory

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "myschedule_database"
        ).build()

        val shiftDao = database.shiftDao()

        val firestore = FirebaseFirestore.getInstance()

        val firestoreShiftDataSource = FirestoreShiftDataSource(firestore = firestore)

        val shiftRepository = ShiftRepository(shiftDao = shiftDao, firestoreShiftDataSource = firestoreShiftDataSource)

        val calendarViewModelFactory = CalendarViewModelFactory(
            shiftRepository = shiftRepository
        )

        setContent {
            MyScheduleTheme {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val calenderViewModel: CalendarViewModel = viewModel(factory = calendarViewModelFactory)
                    CalendarRoute(
                        viewModel = calenderViewModel
                    )
                }
            }
        }
    }
}