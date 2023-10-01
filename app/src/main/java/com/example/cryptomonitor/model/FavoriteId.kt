package com.example.cryptomonitor.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "favorite_id")
data class FavoriteId(
    @PrimaryKey
    val assetId: String,
    var isFavorite: Boolean = false,
) : Parcelable