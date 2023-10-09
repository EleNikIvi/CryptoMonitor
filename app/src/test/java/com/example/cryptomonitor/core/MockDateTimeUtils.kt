package com.example.cryptomonitor.core

import io.mockk.every
import io.mockk.mockkObject

object MockDateTimeUtils {
    fun mockGetCurrentDateTimeFormatted(mockTime: String) {
        mockkObject(DateTimeUtils)
        every { DateTimeUtils.getCurrentDateTimeFormatted() } returns mockTime
    }
}