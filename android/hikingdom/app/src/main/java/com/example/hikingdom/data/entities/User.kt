package com.example.hikingdom.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

// 사용자 정보 데이터 클래스
@Entity(tableName = "UserTable")
data class User(
    @SerializedName("email") val email: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("profileUrl") val profileUrl: String,
    @SerializedName("level") val level: Int,
    @SerializedName("memberId") val memberId: Int,
    @SerializedName("clubId") val clubId: Int? = null,
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

