package com.example.hikingdom.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hikingdom.ApplicationClass.Companion.APP_DATABASE
import com.example.hikingdom.data.entities.User
import com.example.hikingdom.data.entities.UserLocation


@Database(entities = [User::class, UserLocation::class], version = 2, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun userLocationDao(): UserLocationDao

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase? {
            if (instance == null) {
                synchronized(AppDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        APP_DATABASE
                    ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
                }
            }

            return instance
        }
    }
}