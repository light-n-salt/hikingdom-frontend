package com.example.hikingdom.ui.main.hiking

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.hikingdom.ApplicationClass.Companion.TAG
import com.example.hikingdom.R
import com.example.hikingdom.ui.main.MainActivity
import com.example.hikingdom.ui.main.hiking.HikingFragment.Companion.ACTION_STOP
import java.time.LocalDateTime

class HikingForegroundService : Service() {
    var isServiceRunning = false    // Foreground 서비스가 실행중인지 여부

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand 진입")
        if (intent?.action != null
            && intent.action.equals(ACTION_STOP, ignoreCase = true)) {  // "하이킹 종료" 버튼을 눌렀을 때, foreground 서비스를 종료.
            Log.d(TAG, "foreground service 종료")
            stopForeground(true)
            stopSelf()

            isServiceRunning = false
        }else{
            Log.d(TAG, "foreground service 시작")
            createNotification()
            mThread?.start()
            isServiceRunning = true
        }


        return START_STICKY // 서비스가 강제로 종료되었을 때 시스템이 자동으로 다시 시작, 사용자의 위치 정보를 계속 추적해야할 때 적합
                            // https://work2type.tistory.com/entry/STARTSTICKY-STARTNOTSTICKY
    }



    private var mThread: Thread? = object : Thread("Get Location") {
        override fun run() {
            super.run()
            while(isServiceRunning){

                try {
                    sleep(1000)
                    // 위치 정보 가져오기
                    Log.d(TAG, LocalDateTime.now().toString())
                    // 위치 정보 저장하기


                } catch (e: InterruptedException) {
                    currentThread().interrupt()
                    break
                }
            }
            Log.d(TAG, "경로 기록 스레드 종료")
            // while문 빠져나와 run()메서드 종료 => 스레드가 사용 중이던 자원을 정리하고, run()메서드가 끝나게 됨으로써 스레드가 안전하게 종료됨
        }
    }

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
}