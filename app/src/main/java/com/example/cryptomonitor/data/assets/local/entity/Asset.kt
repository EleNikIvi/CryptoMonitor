package com.example.cryptomonitor.data.assets.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "asset")
data class Asset(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val assetId: String,
    val name: String,
    val typeIsCrypto: Int,
    val dataStart: String? = null,
    val dataEnd: String? = null,
    val dataQuoteStart: String? = null,
    val dataQuoteEnd: String? = null,
    val dataOrderBookStart: String? = null,
    val dataOrderBookEnd: String? = null,
    val dataTradeStart: String? = null,
    val dataTradeEnd: String? = null,
    val dataSymbolsCount: Long,
    val volume1HrsUsd: Double,
    val volume1DayUsd: Double,
    val volume1MthUsd: Double,
    val priceUsd: Double? = 0.00,
) : Parcelable