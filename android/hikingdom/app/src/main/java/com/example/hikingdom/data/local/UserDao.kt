package com.example.hikingdom.data.local

import androidx.room.*
import com.example.hikingdom.data.entities.User

@Dao
interface UserDao {
    @Insert
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

}