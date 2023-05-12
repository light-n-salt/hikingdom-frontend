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

    @Query("SELECT * FROM UserTable LIMIT 1")
    fun getUser(): User

    @Query("SELECT * FROM UserTable")
    fun getALLUser(): List<User>
}