package com.example.cryptomonitor.data.repository.assetdetails

import com.example.cryptomonitor.core.DateTimeUtils.formatToLocalDateTime
import com.example.cryptomonitor.data.local.exchangerate.entity.ExchangeRateEntity
import com.example.cryptomonitor.data.remote.exchangerate.ExchangeRateDto

object ExchangeRateMapper {
    fun ExchangeRateDto.toEntity(): ExchangeRateEntity = ExchangeRateEntity(
        rateUpdated = formatToLocalDateTime(time ?: ""),
        assetIdBase = assetIdBase ?: "",
        assetIdQuote = assetIdQuote ?: "",
        rate = rate ?: 0.00,
    )
}