package com.example.hikingdom.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserLocation(
    val latitude : Double,
    val longtitude : Double,
    val altitude : Double
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
