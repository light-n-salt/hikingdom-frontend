package com.example.hikingdom.data.remote.hiking

data class SaveHikingRecordReq(
    val isMeetup: Boolean,
    val mountainId: Long,
    val meetupId: Long?,
    val startAt: String,
    val totalDistance: Int,
    val maxAlt: Double,
    val totalDuration: Int,
    val gpsRoute: List<GpsRoute>
)

data class GpsRoute(
    val lat: Double,
    val lng: Double,
    val alt: Double
)
