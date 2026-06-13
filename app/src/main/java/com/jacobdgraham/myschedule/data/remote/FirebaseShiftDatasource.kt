package com.jacobdgraham.myschedule.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.jacobdgraham.myschedule.data.local.ShiftEntity
import com.jacobdgraham.myschedule.domain.model.ShiftDefinition
import kotlinx.coroutines.tasks.await
import java.time.YearMonth

/**
 * Remote data source responsible for loading shifts from Firestore.
 *
 */
class FirestoreShiftDataSource(
    private val firestore: FirebaseFirestore
) {
    suspend fun getShiftsForMonth(yearMonth: YearMonth): List<ShiftEntity> {
        val startDate = yearMonth.atDay(1).toString()
        val endDateExclusive = yearMonth.plusMonths(1).atDay(1).toString()

        val snapshot = firestore
            .collection("shifts")
            .whereGreaterThanOrEqualTo("date", startDate)
            .whereLessThan("date", endDateExclusive)
            .get()
            .await()

        return snapshot.documents.mapNotNull { document ->
            val date = document.getString("date")
            val shiftCode = document.getString("shiftCode")

            if (date != null && shiftCode != null) {
                ShiftEntity(
                    date = date,
                    shiftCode = shiftCode
                )
            } else {
                null
            }
        }
    }

    suspend fun getShiftDefinitionsForMonth(
        shiftCodes: List<String>
    ): List<ShiftDefinition> {
        if (shiftCodes.isEmpty()) {
            return emptyList()
        }

        return shiftCodes.mapNotNull { code ->
            val document = firestore
                .collection("shiftDefinitions")
                .document(code)
                .get()
                .await()

            if (document.exists()) {
                ShiftDefinition(
                    code = document.getString("code") ?: code,
                    definition = document.getString("definition") ?: ""
                )
            } else {
                null
            }
        }
    }
}