package com.example.hikingdom.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.hikingdom.data.entities.UserLocation

// UserLocation Table에 대한 접근 메소드를 정의
@Dao
interface UserLocationDao {

    @Query("select * from UserLocation")
    fun getUserLocations(): List<UserLocation>

    @Insert
    fun insertUserLocation(userLocation: UserLocation)

    @Query("delete from UserLocation")
    fun deleteAllUserLocations()
}