package com.example.hikingdom.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

// User 테이블. UserDao를 통해 조작
@Entity(tableName = "UserTable")
data class User(
    @SerializedName("email") val email: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("profileUrl") val profileUrl: String,
    @SerializedName("level") val level: Int,
    @SerializedName("memberId") val memberId: Long,
    @SerializedName("clubId") val clubId: Long? = null,
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

