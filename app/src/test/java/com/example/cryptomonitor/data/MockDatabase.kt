package com.example.cryptomonitor.data

import androidx.room.withTransaction
import com.example.cryptomonitor.data.core.local.CryptoMonitorDatabase
import io.mockk.coEvery
import io.mockk.mockkStatic
import io.mockk.slot

object MockDatabase {
    fun mockDatabaseWithTransaction(database: CryptoMonitorDatabase) {
        mockkStatic(
            "androidx.room.RoomDatabaseKt"
        )

        val transactionLambda = slot<suspend () -> Unit>()
        coEvery { database.withTransaction(capture(transactionLambda)) } coAnswers {
            transactionLambda.captured.invoke()
        }
    }
}