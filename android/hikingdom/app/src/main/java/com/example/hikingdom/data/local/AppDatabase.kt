package com.example.hikingdom.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hikingdom.ApplicationClass.Companion.APP_DATABASE
import com.example.hikingdom.data.entities.User
import com.example.hikingdom.data.entities.UserLocation

// 어플리케이션의 RoomDB 인스턴스를 정의한 클래스
@Database(entities = [User::class, UserLocation::class], version = 2, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao // 사용자 정보 Dao
    abstract fun userLocationDao(): UserLocationDao // 사용자 위치정보 Dao

    // database 인스턴스가 없을 경우, 인스턴스 생성
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