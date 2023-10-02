package com.example.cryptomonitor.data.assets.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "favorite")
data class Favorite(
    @PrimaryKey
    val assetId: String,
    var isFavorite: Boolean = false,
) : Parcelable