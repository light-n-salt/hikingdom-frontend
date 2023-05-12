package com.example.hikingdom.ui.main.hiking

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.location.Location
import android.os.*
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData
import com.example.hikingdom.ApplicationClass
import com.example.hikingdom.ApplicationClass.Companion.TAG
import com.example.hikingdom.R
import com.example.hikingdom.data.entities.UserLocation
import com.example.hikingdom.data.local.AppDatabase
import com.example.hikingdom.data.remote.hiking.GpsRoute
import com.example.hikingdom.data.remote.hiking.HikingService
import com.example.hikingdom.data.remote.hiking.SaveHikingRecordReq
import com.example.hikingdom.ui.main.MainActivity
import com.example.hikingdom.ui.main.hiking.HikingFragment.Companion.ACTION_STOP
import com.example.hikingdom.utils.LocationHelper
import com.example.hikingdom.utils.saveIsLocationServiceRunning
import java.time.LocalDateTime

class LocationService : Service(), SaveHikingRecordView {
    lateinit var db: AppDatabase
    var isServiceRunning = false    // Foreground 서비스가 실행중인지 여부
    private val binder = LocationBinder()     // Binder given to clients
    var hikingLocationListener : LocationHelper.HikingLocationListener? = null
    // 현재까지의 이동 거리(m)(누적), 이동 고도(m)(최대고도 - 최소고도), 걸린 시간(초)(종료 시간 - 시작 시간)
    var duration = MutableLiveData<Int>()
    var totalDistance = MutableLiveData<Int>()

    // 위도, 경도, 고도 list
    var locations = MutableLiveData<ArrayList<Location>>()

    // 현재 위치
    var currentLocation = MutableLiveData<Location>()

    var isHikingStarted = MutableLiveData<Boolean>()

    lateinit var startAt: LocalDateTime;

    private lateinit var locationHandler: Handler
    private lateinit var locationLooper: Looper
    private lateinit var timerHandler: Handler
    private lateinit var timerLooper: Looper

    init {
        duration.value = 0
        totalDistance.value = 0
        locations.value = ArrayList()
        isHikingStarted.value = false
    }

    /*
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    inner class LocationBinder : Binder() {
        // 액티비티와 서비스가 연결되면 이 메서드를 통해 서비스에 접근
        fun getService(): LocationService = this@LocationService
    }

    override fun onCreate() {
        super.onCreate()
        db = AppDatabase.getInstance(applicationContext)!!
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand 진입")
        if (intent?.action != null
            && intent.action.equals(ACTION_STOP, ignoreCase = true)) {
            Log.d(TAG, "foreground service 종료")

            stopForeground(true)
            stopSelf()
            isServiceRunning = false
            isHikingStarted.postValue(false)
            LocationHelper().stopListeningUserLocation(this)
            hikingLocationListener = null
            LocationHelper.hikingLocationListener = null

            // sharedPreference에 LocationService 실행 상태 저장 (for HikingFragment의 '하이킹 시작/종료'버튼 처리)
            saveIsLocationServiceRunning(false)

            // 로컬 DB에 지금까지 저장된 위치 데이터 불러오기
            val storedUserLocations = db?.userLocationDao().getUserLocations()
            Log.d("storedUserLocations", storedUserLocations.toString())
            // storedUserLocations를 post api로 서버에 전달, success 시 모든 userLocation 데이터 삭제
            var gpsRoute = ArrayList<GpsRoute>()
            for (storedUserLocation in storedUserLocations){
                gpsRoute.add(GpsRoute(storedUserLocation.latitude, storedUserLocation.longitude, storedUserLocation.altitude))
            }
            val maxAlt = storedUserLocations.maxByOrNull { it.altitude }?.altitude
            val minAlt = storedUserLocations.minByOrNull { it.altitude }?.altitude
            val totalAlt = maxAlt!! - minAlt!!
            var saveHikingRecordReq = SaveHikingRecordReq(false, 2, null, ApplicationClass().localDateTimeToString(startAt),
                totalDistance.value!!, totalAlt, duration.value!!, gpsRoute)
            HikingService.saveHikingRecord(this, saveHikingRecordReq)
        }else{
            Log.d(TAG, "foreground service 시작")

            db?.userLocationDao().deleteAllUserLocations()  // 룸db에 저장되어 있는 내용 전부 삭제

            LocalDateTime.now()
            startAt = LocalDateTime.now()

            createNotification()
            isServiceRunning = true
            isHikingStarted.postValue(true)

            // 핸들러와 루퍼를 생성합니다.
            val locationHandlerThread = HandlerThread("location_update_thread")
            locationHandlerThread.start()
            locationLooper = locationHandlerThread.looper
            locationHandler = Handler(locationLooper)

            hikingLocationListener = object : LocationHelper.HikingLocationListener {          // viewModel 코드는 그대로 두고, 로컬에도 저장하도록 수정해야함.
                override fun onLocationChanged(location: Location) {
                    locationHandler.post {
                        // Here you got user location :)
                        Log.d("Location","" + location.latitude + "," + location.longitude + ","+location.altitude)
                        if (currentLocation.value != null){  // 처음 위치정보를 가져왔다면 pass
                            val distance = location.distanceTo(currentLocation.value).toInt()
                            totalDistance.postValue(totalDistance.value?.plus(distance))
                        }
                        currentLocation.postValue(location)
                        locations.value?.add(location)

                        // 위치정보를 RoomDB에 저장
                        db!!.userLocationDao().insertUserLocation(UserLocation(location.latitude, location.longitude, location.altitude))
                    }
                }
            }

            LocationHelper().startListeningUserLocation(this, hikingLocationListener)

            // 타이머 핸들러스레드 생성
            val timernHandlerThread = HandlerThread("timer_thread")
            timernHandlerThread.start()
            timerLooper = timernHandlerThread.looper
            timerHandler = Handler(timerLooper)

            // sharedPreference에 LocationService 실행 상태 저장 (for HikingFragment의 '하이킹 시작/종료'버튼 처리)
            saveIsLocationServiceRunning(true)
        }

        return START_STICKY
    }
//    private var mThread: Thread? = object : Thread("calculate duration") {
//        override fun run() {
//            super.run()
//            while(isServiceRunning){
//
//                try {
//                    sleep(1000)
//                    duration.postValue(duration.value?.plus(1))
//                } catch (e: InterruptedException) {
//                    currentThread().interrupt()
//                    break
//                }
//            }
//            Log.d(TAG, "경로 기록 스레드 종료")
//            // while문 빠져나와 run()메서드 종료 => 스레드가 사용 중이던 자원을 정리하고, run()메서드가 끝나게 됨으로써 스레드가 안전하게 종료됨
//        }
//    }

    //Notififcation for ON-going
    private var iconNotification: Bitmap? = null
    private var notification: Notification? = null
    var mNotificationManager: NotificationManager? = null
    private val mNotificationId = 100

    private fun createNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val intentMainLanding = Intent(this, MainActivity::class.java)
            val pendingIntent =
                PendingIntent.getActivity(this, 0, intentMainLanding, PendingIntent.FLAG_MUTABLE)
            iconNotification = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
            if (mNotificationManager == null) {
                mNotificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                assert(mNotificationManager != null)
                mNotificationManager?.createNotificationChannelGroup(
                    NotificationChannelGroup("locations_group", "Locations")
                )
                val notificationChannel =
                    NotificationChannel("service_channel", "Service Notifications",
                        NotificationManager.IMPORTANCE_MIN)
                notificationChannel.enableLights(false)
                notificationChannel.lockscreenVisibility = Notification.VISIBILITY_SECRET
                mNotificationManager?.createNotificationChannel(notificationChannel)
            }
            val builder = NotificationCompat.Builder(this, "service_channel")

            builder.setContentTitle(StringBuilder("Hikingdom"))
                .setTicker(StringBuilder("등산 경로 기록을 시작합니다!"))
                .setContentText(StringBuilder("Hikingdom에서 등산 경로를 기록중입니다"))
                .setSmallIcon(R.drawable.ic_hiking)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setWhen(0)
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .color = Color.RED
            if (iconNotification != null) {
                builder.setLargeIcon(Bitmap.createScaledBitmap(iconNotification!!, 128, 128, false))
            }
            notification = builder.build()
            startForeground(mNotificationId, notification)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true)
        stopSelf()
        saveIsLocationServiceRunning(false)
    }

    override fun onSaveHikingRecordSuccess(message: String) {
        db?.userLocationDao().deleteAllUserLocations()  // 나중에 지우기 (api 호출 onSuccess에서 처리해줘야함)
        Log.d("clearedUserLocations", db?.userLocationDao().getUserLocations().toString())
        Log.d("saveHikingRecordSuccess", message)
    }

    override fun onSaveHikingRecordFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        Log.d("saveHikingRecordFailure", message)
    }
}