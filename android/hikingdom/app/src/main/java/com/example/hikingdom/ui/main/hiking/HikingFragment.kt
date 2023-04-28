package com.example.hikingdom.ui.main.hiking

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.IBinder
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.hikingdom.BuildConfig
import com.example.hikingdom.databinding.FragmentHikingBinding
import com.example.hikingdom.ui.BaseFragment
import com.example.hikingdom.ui.main.group.GroupFragment
import java.time.LocalDateTime

class HikingFragment(): BaseFragment<FragmentHikingBinding>(FragmentHikingBinding::inflate) {
    private lateinit var locationService : LocationService
    private val hikingViewModel : HikingViewModel by viewModels()

    private var bound: Boolean = false
    lateinit var startTime: LocalDateTime
    lateinit var finishTime: LocalDateTime

    override fun initAfterBinding() {
        binding.lifecycleOwner = this
        binding.hikingFragmentViewModel = hikingViewModel

        setHikingService()
    }

    companion object {
        fun newInstance(): GroupFragment = GroupFragment()
        const val  ACTION_STOP = "${BuildConfig.APPLICATION_ID}.stop"
    }

    // Service와 HikingFragment를 Binding
    /* Defines callbacks for service binding, passed to bindService()  */
    // bound service를 통해 Activity 등 구성요소를 서비스에 바인딩하고, 요청을 보내고, 응답을 수신하며, 프로세스 간 통신(IPC)을 실행할 수 있다.
    // HikingFragment의 액티비티인 MainActivity를 서비스에 바인딩한다.
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, ibinder: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            val binder = ibinder as LocationService.LocationBinder
            locationService = binder.getService()
            bound = true

            // 사용자의 실시간 위치 정보 화면에 띄워주기
            loadLocationInfo()
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            bound = false
        }
    }

    fun setHikingService(){
        val hikingStartBtn = binding.hikingStartBtn
        val hikingFinishBtn = binding.hikingFinishBtn
        hikingStartBtn.setOnClickListener {
            if (!checkPermission()) {
                requestPermission()
                if(checkPermission()){
                    startHikingService()
                    showToast("등산 기록을 시작합니다.")
                    hikingFinishBtn.visibility = View.VISIBLE
                    hikingStartBtn.visibility = View.GONE
                }else{
                    showToast("위치 권한을 허용하지 않으면 시작할 수 없습니다.")
                }
            }
            else {
                startHikingService()
                showToast("등산 기록을 시작합니다.")
                hikingFinishBtn.visibility = View.VISIBLE
                hikingStartBtn.visibility = View.GONE
            }


        }

        hikingFinishBtn.setOnClickListener {
            val stopServiceIntent = Intent(activity, LocationService::class.java)
            stopServiceIntent.action = ACTION_STOP
            activity?.startService(stopServiceIntent)
            Intent(activity, LocationService::class.java).also{
                    intent ->
                activity?.unbindService(connection)
            }
            bound = false
            showToast("등산 기록을 종료합니다.")
            hikingFinishBtn.visibility = View.GONE
            hikingStartBtn.visibility = View.VISIBLE

            finishTime = LocalDateTime.now()    // 종료시간 세팅
        }
    }

    fun loadLocationInfo() {

        locationService.totalDistance.observe(this) {
            Log.d("HikingFragment", it.toString())
            hikingViewModel.setTotalDistance(it)
        }

        locationService.duration.observe(this) {
            hikingViewModel.setDuration(it)
        }

        locationService.lastLocation.observe(this){
            hikingViewModel.setLastLocation(it)
        }

//        locationService.lastLocation.observe(this){
//            hikingViewModel.setTotalAltitude(it.altitude)
//        }
    }

    private fun checkPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(this.requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
        val result1 = ContextCompat.checkSelfPermission(this.requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this.requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 1)
    }

    fun startHikingService(){
        val startServiceIntent = Intent(activity, LocationService::class.java)
//            ContextCompat.startForegroundService(this.requireContext(), Intent(this.requireContext(), LocationService::class.java))
        activity?.startService(startServiceIntent)
        Intent(activity, LocationService::class.java).also{
                intent ->
            activity?.bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }

        startTime = LocalDateTime.now()     // 시작시간 세팅
    }

//    fun checkPermission(){
//        // check for permissions
//        if (ContextCompat.checkSelfPermission(this.requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
//            && ContextCompat.checkSelfPermission(this.requireContext(),
//                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME.toLong(),LOCATION_REFRESH_DISTANCE.toFloat(), mLocationListener)
//        } else {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)
//                || ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.ACCESS_COARSE_LOCATION)) {
//                // permission is denined by user, you can show your alert dialog here to send user to App settings to enable permission
//            } else {
//                ActivityCompat.requestPermissions(context,arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),MY_PERMISSIONS_REQUEST_LOCATION)
//            }
//        }
//    }

}