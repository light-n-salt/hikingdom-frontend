package com.example.hikingdom.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

// UserLocation 테이블. UserLocationDao를 통해 조작
@Entity
data class UserLocation(
    val latitude : Double,
    val longitude : Double,
    val altitude : Double
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
