package com.example.hikingdom.data.remote.hiking

import com.example.hikingdom.data.remote.auth.Token
import com.google.gson.annotations.SerializedName

data class Mountain(
    @SerializedName("mountainId") val mountainId: Long,
    @SerializedName("name") val name: String,
    @SerializedName("address") val address: String,
    @SerializedName("maxAlt") val maxAlt: Double,
    @SerializedName("imgUrl") val imgUrl: String,
    @SerializedName("mountainSummitLat") val mountainSummitLat: Double,
    @SerializedName("mountainSummitLng") val mountainSummitLng: Double,
)

data class MountainResponse(
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: List<Mountain>,
)

data class Meetup(
    @SerializedName("meetupId") val meetupId: Int,
    @SerializedName("meetupName") val name: String,
    @SerializedName("mountainId") val mountainId: Int,
    @SerializedName("mountainName") val mountainName: String,
    @SerializedName("mountainSummitLat") val mountainSummitLat: Float,
    @SerializedName("mountainSummitLng") val mountainSummitLng: Float,
    @SerializedName("totalMember") val totalMember: Int,
    @SerializedName("startAt") val startAt: String,
)

data class MeetupResponse(
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: List<Meetup>,
)
