package com.example.cryptomonitor.data.local.assets.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "icon")
data class IconEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val assetId: String,
    val url: String,
) : Parcelable
