package com.jacobdgraham.myschedule.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

/**
 * This class assumes Firestore data structure is the following:
 * ```
 * schedules/
 *  2026-05
 *      year: 2026
 *      month: 5
 *      days:
 *          1: "2010"
 *          2: "2010"
 *          3: "1510"
 *          4: "0610"
 * ```
 * @param firestore the connection to the firebase data source
 */
class FirebaseShiftDatasource(private val firestore: FirebaseFirestore) {

    /**
     * @param year integer representation of year, where the year must be 4 digits or longer
     * @param month integer representation of month, where the month must be 2 digits or longer
     * @return if valid data from firebase, create a valid [ShiftDto] object and return it. Else, null
     */
    suspend fun getMonthSchedule(year: Int, month: Int): ShiftDto? {
        val documentId = "%04d-%02d".format(year, month)

        val snapshot = firestore
            .collection("schedules")
            .document(documentId)
            .get()
            .await()

        return snapshot.toObject(ShiftDto::class.java)
    }
}