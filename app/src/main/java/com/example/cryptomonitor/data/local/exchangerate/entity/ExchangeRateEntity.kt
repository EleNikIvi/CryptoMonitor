package com.example.cryptomonitor.data.local.exchangerate.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "exchange_rate")
data class ExchangeRateEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val rateUpdated: String,
    val assetIdBase: String,
    val assetIdQuote: String,
    val rate: Double,
) : Parcelable