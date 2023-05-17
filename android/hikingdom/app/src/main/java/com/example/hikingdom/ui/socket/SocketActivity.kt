package com.example.hikingdom.ui.socket


import android.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.graphics.Shader
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.example.hikingdom.R
import com.example.hikingdom.data.remote.hiking.SocketEnterData
import com.example.hikingdom.data.remote.hiking.SocketGPSData
import com.example.hikingdom.databinding.ActivitySocketBinding
import com.example.hikingdom.ui.BaseActivity
import com.example.hikingdom.utils.LocationUtils
import com.google.gson.Gson
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import org.json.JSONException
import org.json.JSONObject
import java.security.MessageDigest
import java.util.Timer
import java.util.TimerTask


class SocketActivity: BaseActivity<ActivitySocketBinding>(ActivitySocketBinding::inflate) {

    lateinit var mapView: MapView

    private var mSocket: Socket? =null;
    private var nickname: String? = null
    private var profileUrl: String? = null
    private var level: Int? = null
    private var meetupId: Long? = null
    private var clubId: Long? = null
    private var memberId: Long? = null

    val gson: Gson = Gson()

    val timer = Timer()
    lateinit var task: TimerTask

    override fun initAfterBinding() {
        mapView = MapView(this)

        // 지도 띄우기
        val mapViewContainer = binding.mapView
        mapViewContainer.addView(mapView)


        // 클릭 시 소켓 연결
        binding.btnConnect.setOnClickListener {
            connectSocket()
        }
        // 클릭 시 위치공유 시작
        binding.btnSend.setOnClickListener {
            sendGPSPeriodically()
        }
    }

    override fun onDestroy() {
        super.onDestroy();
        mSocket?.emit("leave", gson.toJson(SocketEnterData(memberId, meetupId,)))
        mSocket?.disconnect();
        mSocket?.off("enter")
        mSocket?.off("newLocation")
        timer.cancel()
        task.cancel()
    }

    private fun connectSocket() {
        // 데이터 할당
        val user = db?.userDao()?.getUser()
        nickname = user?.nickname
        profileUrl = user?.profileUrl
        level = user?.level
        memberId = user?.memberId
        clubId = user?.clubId
        meetupId = 1

        Log.d("SOCKET1", "Connection success : " + mSocket)

        // 소켓 생성
        try {
//            mSocket = IO.socket("https://hikingdom.kr/api/hiking")
//            mSocket = IO.socket("http://hikingdom.kr:8081", {})
            mSocket = IO.socket("http://hikingdom.kr:8003")
//            mSocket = IO.socket("http://192.168.219.102:3000")
//            mSocket = IO.socket("http://70.12.246.181:3000")
            Log.d("SOCKET2", "Connection success : " + mSocket?.id())
            Log.d("SOCKET3", "Connection success : " + mSocket)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("SOCKET", "소켓연결에 실패했습니다." )
        }

        Log.d("SOCKET4", "Connection success : " + mSocket)


        // 소켓 연결 시 입장 이벤트 발생, subscribe
        mSocket?.on(
            Socket.EVENT_CONNECT
        ) { args: Array<Any?>? ->
            Log.d("SOCKET5", "소켓 입장" )
            mSocket?.emit("enter", gson.toJson(SocketEnterData(memberId, meetupId,))) // 입장 메시지 전송
        }
        mSocket?.on("enter"){ args: Array<Any> ->
            val data: SocketEnterData = gson.fromJson(
                args[0].toString(),
                SocketEnterData::class.java
            )
            onEnter(data)
        }
        mSocket?.on("newLocation") { args: Array<Any> ->
            val data: SocketGPSData = gson.fromJson(
                args[0].toString(),
                SocketGPSData::class.java
            )
            onNewLocation(data)
        }

        // 소켓 연결
        mSocket?.connect()
    }

    private fun sendGPSPeriodically() {
        task = object : TimerTask() {
            override fun run() {
                sendGPS()
            }
        }
        timer.schedule(task, 0, 1000)
    }

    private fun sendGPS() {
        // 현재 위치 정보 받아오기
        val locationInfo = LocationUtils.getCurrentLocation(this)

        Log.d("sendLocation1", locationInfo.toString());

        if (locationInfo == null) {
            Toast.makeText(this, "위치 정보를 받아오지 못했습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("sendLocation2", gson.toJson(
            SocketGPSData(
                nickname,
                profileUrl,
                level,
                memberId,
                clubId,
                meetupId,
                locationInfo[0],
                locationInfo[1],
            )))
        // 소켓 보내기
        mSocket?.emit(
            "newLocation", gson.toJson(
                SocketGPSData(
                    nickname,
                    profileUrl,
                    level,
                    memberId,
                    clubId,
                    meetupId,
                    locationInfo[0],
                    locationInfo[1],
                )
            )
        )
    }

    // 입장 시 동작
    private fun onEnter(data: SocketEnterData) {
        Log.d("enter", data.toString())
    }

    // gps 정보 수신 시, 커스텀 마커 업데이트
    private fun onNewLocation(data: SocketGPSData) {
        runOnUiThread {
            // 데이터 추출
            val nickname = data.nickname
            val memberId = data.memberId
            val profileUrl = data.profileUrl
            val lat = data.lat
            val lng = data.lng

            Log.d("receivedLocation",  nickname + " " + lat + " " + lng  )

            // 커스텀 마커 생성
            val customMarker = MapPOIItem()
            customMarker.itemName = nickname
            customMarker.tag = (memberId!! + 1).toInt()
            customMarker.mapPoint = MapPoint.mapPointWithGeoCoord(lat!!, lng!!)
            customMarker.markerType = MapPOIItem.MarkerType.CustomImage // 마커타입을 커스텀 마커로 지정.
            customMarker.isCustomImageAutoscale =
                false // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
            customMarker.setCustomImageAnchor(0.5f, 1.0f) // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.
            Glide.with(this)
                .asBitmap()
                .load(profileUrl)
                .transform(CircleCropWithTriangleTransformation())
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                    ) {
                        customMarker.customImageBitmap = resource

                        // 기존 마커 삭제 후 새 마커 추가
                        val removeItems = mapView.findPOIItemByName(nickname)
                        if (removeItems != null) {
                            mapView.removePOIItems(removeItems)
                        }
                        mapView.addPOIItem(customMarker)
                        Log.d("추가", "추가")
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        customMarker.customImageResourceId = R.drawable.custom_marker_red
                        mapView.addPOIItem(customMarker)

                        Log.d("실패", "실패")
                    }
                })
        }
    }

}

// 커스텀 마커 생성
class CircleCropWithTriangleTransformation : BitmapTransformation() {

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        val size = 230 // 원의 크기
        val paintWidth = 40 // 테두리의 너비
        val triangleSize = 40 // 역삼각형의 크기

        val bitmap = pool.get(size, size, Bitmap.Config.ARGB_8888) // 32비트 ARGB 형식의 정사각형 비트맵
        val canvas = Canvas(bitmap)
        val paint = Paint().apply {
            isAntiAlias = true
        }

        // 흰색 테두리 그리기
        paint.style = Paint.Style.STROKE
        paint.color = Color.WHITE
        paint.strokeWidth = paintWidth.toFloat() * toTransform.density
        canvas.drawCircle(size / 2f, size / 2f, size / 2f + paintWidth, paint)

        // 역삼각형 하단에 추가
        paint.style = Paint.Style.FILL
        val trianglePath = Path()
        val centerX = size / 2f
        val centerY = size - paintWidth.toFloat()
        val halfTriangleSize = triangleSize / 2f
        trianglePath.moveTo(centerX - halfTriangleSize, centerY)
        trianglePath.lineTo(centerX + halfTriangleSize, centerY)
        trianglePath.lineTo(centerX, centerY + triangleSize)
        trianglePath.close()
        canvas.drawPath(trianglePath, paint)

        // 이미지를 원 형태로 크롭
        val shader = BitmapShader(toTransform, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.style = Paint.Style.FILL
        paint.shader = shader
        canvas.drawCircle(size / 2f, size / 2f, size / 2f - paintWidth , paint)

        return bitmap
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
    }

    override fun hashCode(): Int {
        return ID.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return other is CircleCropWithTriangleTransformation
    }

    companion object {
        private const val ID = "com.example.CircleCropWithTriangleTransformation"
        private val ID_BYTES: ByteArray = ID.toByteArray(Charsets.UTF_8)
    }
}
