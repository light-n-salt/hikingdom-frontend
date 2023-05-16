package com.example.hikingdom.ui.socket


import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.example.hikingdom.R
import com.example.hikingdom.data.remote.hiking.GpsSocketData
import com.example.hikingdom.databinding.ActivitySocketBinding
import com.example.hikingdom.ui.BaseActivity
import com.example.hikingdom.utils.LocationUtils
import com.google.gson.GsonBuilder
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import org.json.JSONException
import org.json.JSONObject
import java.net.URISyntaxException


class SocketActivity: BaseActivity<ActivitySocketBinding>(ActivitySocketBinding::inflate) {

    lateinit var mapView: MapView
    private var mSocket: Socket? = null
    private var nickname: String? = null
    private var profileUrl: String? = null
    private var level: Int? = null
    private var meetupId: Int? = null
    private var clubId: Int? = null
    private var memberId: Int? = null

    val gson  = GsonBuilder().setLenient().create()

    override fun initAfterBinding() {
        mapView = MapView(this)

        // 지도 띄우기
        val mapViewContainer = binding.mapView
        mapViewContainer.addView(mapView)

        // 지도 중심 잡기
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.53737528, 127.00557633), true);

        // 클릭 시 소켓 연결
        binding.btnSend.setOnClickListener {
            // 소켓 연결 하기
            connectSocket()
        }
    }

    override fun onDestroy() {
        super.onDestroy();
        mSocket?.disconnect();
        mSocket?.off("new message", onNewLocation);
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

        // 소켓 연결
        try {
            mSocket = IO.socket("http://hikindom.kr:8081/api/hiking")
            Log.d("SOCKET", "Connection success : " + mSocket?.id())
        } catch (e: URISyntaxException) {
            e.printStackTrace()
            Log.d("SOCKET", "소켓연결에 실패했습니다." )
        }

        // 소켓 연결 시 입장 이벤트 발생, subscribe
        mSocket?.on(
            Socket.EVENT_CONNECT
        ) { args: Array<Any?>? ->
            mSocket?.emit("enter") // 입장 메시지 전송
            mSocket?.on("new message", onNewLocation); // subscribe
            // 주기적으로 정보 전달
        }
    }

    private fun sendGPS() {
        // 현재 위치 정보 받아오기
        val locationInfo = LocationUtils.getCurrentLocation(this);

        if (locationInfo == null) {
            Toast.makeText(this, "위치 정보를 받아오지 못했습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("sendLocation", gson.toJson(
            GpsSocketData(
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
                GpsSocketData(
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

    // 새 위치 정보 왔을 때 동작
    private val onNewLocation =
        Emitter.Listener { args ->
            this.runOnUiThread(Runnable {
                val data = args[0] as JSONObject
                val username: String
                val message: String
                try {
                    var nickname = data.getString("nickanme")
                    var profileUrl = data.getString("profileUrl")
                    var level = data.getString("level")
                    var memberId = data.getString("memeberId")
                    var clubId = data.getString("clubId")
                    var meetupId= data.getString("meetupId")
                    var lat = data.getString("lat")
                    var lng = data.getString("lng")
                    Log.d("receivedLocation", data.toString())
                } catch (e: JSONException) {
                    return@Runnable
                }

            })
        }

    // 커스텀 마커 추가
    private fun addMarker() {
        // 커스텀 마커 추가
        val customMarker = MapPOIItem()
        customMarker.itemName = "Custom Marker"
        customMarker.tag = 1
        customMarker.mapPoint = MapPoint.mapPointWithGeoCoord(37.53737528, 127.00557633)
        customMarker.markerType = MapPOIItem.MarkerType.CustomImage // 마커타입을 커스텀 마커로 지정.
        customMarker.isCustomImageAutoscale = false // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
        customMarker.setCustomImageAnchor(
            0.5f,
            1.0f
        ) // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.
        Glide.with(this)
            .asBitmap()
            .load("https://cdn.crowdpic.net/list-thumb/thumb_l_CDD94CBD46425E4EDBD18A7A17C199E7.jpg")
//            .apply(requestOptions)
            .apply(RequestOptions.circleCropTransform())
            .into(object : CustomTarget<Bitmap>() {

                override fun onResourceReady(
                    resource: Bitmap,
                    transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                ) {
                    // Create a square-cropped bitmap
                    val size = resource.width.coerceAtMost(resource.height)
                    val squareBitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
                    val canvas = Canvas(squareBitmap)
                    val x = (resource.width - size) / 2
                    val y = (resource.height - size) / 2
                    canvas.drawBitmap(resource, Rect(x, y, x + size, y + size), Rect(0, 0, size, size), null)

                    // Add white border
                    val borderWidth = 20 // Specify the width of the border
                    val borderedBitmap = Bitmap.createBitmap(size + 2 * borderWidth, size + 2 * borderWidth, Bitmap.Config.ARGB_8888)
                    val borderedCanvas = Canvas(borderedBitmap)
                    borderedCanvas.drawColor(Color.WHITE)
                    borderedCanvas.drawBitmap(squareBitmap, borderWidth.toFloat(), borderWidth.toFloat(), null)

                    customMarker.customImageBitmap = borderedBitmap
                    mapView.addPOIItem(customMarker)
                }
                override fun onLoadCleared(placeholder: Drawable?) {
                    customMarker.customImageResourceId = R.drawable.custom_marker_red
                    mapView.addPOIItem(customMarker)
                }
            })
    }

}
