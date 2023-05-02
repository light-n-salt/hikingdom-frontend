package com.example.hikingdom.ui.main.hiking

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.IBinder
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.hikingdom.BuildConfig
import com.example.hikingdom.R
import com.example.hikingdom.databinding.FragmentHikingBinding
import com.example.hikingdom.ui.BaseFragment
//import com.example.hikingdom.ui.main.group.GroupFragment
import net.daum.mf.map.api.*
import java.time.LocalDateTime


class HikingFragment(): BaseFragment<FragmentHikingBinding>(FragmentHikingBinding::inflate)
//    , MapView.CurrentLocationEventListener
{
    private lateinit var locationService : LocationService
    private val hikingViewModel : HikingViewModel by viewModels()

    private var bound: Boolean = false
    lateinit var startTime: LocalDateTime
    lateinit var finishTime: LocalDateTime

    lateinit var mapView: MapView
    lateinit var mapViewContainer: ConstraintLayout
    val customMarker = MapPOIItem()

    private var lastLat: Double = 0.0
    private var lastLng: Double = 0.0

    private val ZOOM_LEVEL = 2

    private val POLYLINE_COLOR_CODE = 0xFF0F7BDF.toInt()

    override fun initAfterBinding() {
        binding.lifecycleOwner = this
        binding.hikingFragmentViewModel = hikingViewModel

        mapView = MapView(requireContext())
        mapViewContainer = binding.hikingMapview

        if(!checkPermission()){
            requestPermission()
        }
        if(checkPermission()){  // 위치권한 요청 후 다시 확인
            setHikingService()
            setMapView()
            drawPolylineByExistingTrackingData()
            setMarkerSetting()
        } else {
            showToast("22위치 권한을 항상 허용하지 않으면 백그라운드 경로 기록 서비스를 이용하실 수 없습니다.")
        }
    }

    companion object {
        fun newInstance(): HikingFragment = HikingFragment()
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
            startHikingService()
            showToast("등산 기록을 시작합니다.")
            hikingFinishBtn.visibility = View.VISIBLE
            hikingStartBtn.visibility = View.GONE
//            if (!checkPermission()) {
//                requestPermission()
//                if(checkPermission()){
//                    startHikingService()
//                    showToast("등산 기록을 시작합니다.")
//                    hikingFinishBtn.visibility = View.VISIBLE
//                    hikingStartBtn.visibility = View.GONE
//                }else{
//                    showToast("위치 권한을 허용하지 않으면 시작할 수 없습니다.")
//                }
//            }
//            else {
//                startHikingService()
//                showToast("등산 기록을 시작합니다.")
//                hikingFinishBtn.visibility = View.VISIBLE
//                hikingStartBtn.visibility = View.GONE
//            }


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

        locationService.currentLocation.observe(this){
            hikingViewModel.setCurrentLocation(it)

            if (lastLat == 0.0 && lastLng == 0.0){
                Log.d("polyline test 1", StringBuilder("lastLat: ").append(lastLat.toString()).append("/ lastLng: ").append(lastLng.toString()).toString())
                lastLat = it.latitude
                lastLng = it.longitude

                setDepartMarker(lastLat, lastLng)
            }else{
                val polyline = MapPolyline()
                Log.d("polyline test 2", StringBuilder("lastLat: ").append(lastLat.toString()).append("/ lastLng: ").append(lastLng.toString())
                    .append(lastLng.toString()).append("/ lat: ").append(it.latitude).append("/ lng: ").append(it.longitude).toString())
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(lastLat, lastLng));
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(it.latitude, it.longitude))
                polyline.lineColor = POLYLINE_COLOR_CODE
                //                polyline.setLineColor(R.color.blue)
//                polyline.lineColor = Color.rgb(255,204,0)
//                polyline.lineColor = Color.rgb(15.0f,123.0f,223.0f)  // @color/blue 에 해당하는 rgb color
//                polyline.lineColor = Color.argb(1, 15, 123, 223)
                mapView.addPolyline(polyline)
//                mapView.fitMapViewAreaToShowPolyline(polyline)

                lastLat = it.latitude
                lastLng = it.longitude
            }
            val currentMapPoint = MapPoint.mapPointWithGeoCoord(lastLat, lastLng)
            mapView.setMapCenterPoint(currentMapPoint, true)
        }


//        locationService.lastLocation.observe(this){
//            hikingViewModel.setTotalAltitude(it.altitude)
//        }
    }

    private fun checkPermission(): Boolean {
        val isFineLocationPermissionGranted = ContextCompat.checkSelfPermission(this.requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
        val isCoarseLocationPermissionGranted = ContextCompat.checkSelfPermission(this.requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
        val isBackgroundLocationPermissionGranted = ContextCompat.checkSelfPermission(this.requireContext(), Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        return isFineLocationPermissionGranted == PackageManager.PERMISSION_GRANTED
                && isCoarseLocationPermissionGranted == PackageManager.PERMISSION_GRANTED
                && isBackgroundLocationPermissionGranted == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this.requireActivity(), arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 1)
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

    private fun setMapView(){
        mapViewContainer.addView(mapView)

//        Log.d("zoomLevel:",mapView.zoomLevelFloat.toString())
//        mapView.setZoomLevel(ZOOM_LEVEL, true);
//        mapView.isShowingCurrentLocationMarker
//        mapView.setCurrentLocationEventListener(this)
//        mapView.setMapViewEventListener(this)

        /* 현위치 트래킹 모드 및 나침반 모드를 설정한다.
        TrackingModeOnWithHeading: 현위치 트랙킹 모드 On + 나침반 모드 On, 단말의 위치에 따라 지도 중심이 이동하며 단말의 방향에 따라 지도가 회전한다. */
        mapView.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading //
//        if(mapView.isShowingCurrentLocationMarker){
//            Log.d("isShowing 1", "true")
//        }else{
//            Log.d("isShowing 1", "false")
//        }
        mapView.setShowCurrentLocationMarker(false)
//        if(mapView.isShowingCurrentLocationMarker){
//            Log.d("isShowing 2", "true")
//        }else{
//            Log.d("isShowing 2", "false")
//        }
//        LocationHelper().startListeningUserLocation(requireContext() , object : LocationHelper.HikingLocationListener {
//            override fun onLocationChanged(location: Location) {
//                // Here you got user location :)
//                Log.d("first Location","" + location.latitude + "," + location.longitude)
//                val currentMapPoint = MapPoint.mapPointWithGeoCoord(location.latitude, location.longitude)
//                //이 좌표로 지도 중심 이동
//                mapView.setMapCenterPoint(currentMapPoint, true)
//            }
//        })
//        mapView.setShowCurrentLocationMarker(false)



//        mapView.addPOIItem(customMarker)

    }

    private fun setMarkerSetting(){    // 마커를 찍기 위한 기본 세팅
        customMarker.itemName = "Custom Marker"
        customMarker.tag = 1
//        val mapPoint = MapPoint.mapPointWithGeoCoord(37.480426, 126.900177) //마커 표시할 위도경도
//        customMarker.mapPoint = MARKER_POINT
        customMarker.markerType = MapPOIItem.MarkerType.CustomImage // 마커타입을 커스텀 마커로 지정.

//        customMarker.customImageResourceId = R.drawable.custom_marker_red // 마커 이미지.

        customMarker.isCustomImageAutoscale = false // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.

        customMarker.setCustomImageAnchor(
            0.5f,
            1.0f
        ) // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.
    }

    private fun drawPolylineByExistingTrackingData(){
        val polyline = MapPolyline()
        polyline.lineColor = POLYLINE_COLOR_CODE  // @color/blue 에 해당하는 rgb color

        val existingLocationList = hikingViewModel.locations.value
        if(!existingLocationList.isNullOrEmpty()){

            val firstExistingLocation = existingLocationList[0]
            setDepartMarker(firstExistingLocation.latitude, firstExistingLocation.longitude)

            for(location in existingLocationList){
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(location.latitude, location.longitude))
            }
            polyline.lineColor = POLYLINE_COLOR_CODE  // @color/blue 에 해당하는 rgb color
            mapView.addPolyline(polyline)

            val lastExistingLocation = existingLocationList.last()
            lastLat = lastExistingLocation.latitude
            lastLng = lastExistingLocation.longitude

            // 지도뷰의 중심좌표와 줌레벨을 Polyline이 모두 나오도록 조정.
            // 지도뷰의 중심좌표와 줌레벨을 Polyline이 모두 나오도록 조정.
            val mapPointBounds = MapPointBounds(polyline.mapPoints)
            val padding = 100 // px

            mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding))
        }
    }

//    override fun onCurrentLocationUpdate(mv: MapView?, mapPoint: MapPoint?, accuracyInMeters: Float) {
//        val mapPointGeo: GeoCoordinate = mapPoint!!.mapPointGeoCoord
//        Log.i(
//            TAG,
//            String.format(
//                "MapView onCurrentLocationUpdate (%f,%f) accuracy (%f)",
//                mapPointGeo.latitude,
//                mapPointGeo.longitude,
//                accuracyInMeters
//            )
//        )
//        val currentMapPoint = MapPoint.mapPointWithGeoCoord(mapPointGeo.latitude, mapPointGeo.longitude)
//        //이 좌표로 지도 중심 이동
//        mapView.setMapCenterPoint(currentMapPoint, true)
//        //트래킹 모드가 아닌 단순 현재위치 업데이트일 경우, 한번만 위치 업데이트하고 트래킹을 중단시키기 위한 로직
//        mapView.setShowCurrentLocationMarker(false)
//        mapView.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOff
//    }
//
//    override fun onCurrentLocationDeviceHeadingUpdate(p0: MapView?, p1: Float) {
//        TODO("Not yet implemented")
//    }
//
//    override fun onCurrentLocationUpdateFailed(p0: MapView?) {
//        TODO("Not yet implemented")
//    }
//
//    override fun onCurrentLocationUpdateCancelled(p0: MapView?) {
//        TODO("Not yet implemented")
//    }

    private fun setDepartMarker(lat: Double, lng: Double){
        // 출발 좌표에 마커 그리기
//        val firstExistingLocation = existingLocationList[0]
        val customMarker = MapPOIItem()
        customMarker.itemName = "Custom Marker"
        customMarker.tag = 1
        customMarker.mapPoint = MapPoint.mapPointWithGeoCoord(lat, lng)
        customMarker.markerType = MapPOIItem.MarkerType.CustomImage // 마커타입을 커스텀 마커로 지정.

        customMarker.customImageResourceId = R.drawable.custom_marker_red_25 // 마커 이미지.

        customMarker.isCustomImageAutoscale = false // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.

        customMarker.setCustomImageAnchor(
            0.7f,
            1.0f
        ) // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.


        mapView.addPOIItem(customMarker)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("fragment lifecycle", "onDestroyView")
    }

    override fun onPause() {
        super.onPause()
        Log.d("fragment lifecycle", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("fragment lifecycle", "onStop")
        mapViewContainer.removeAllViews()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("fragment lifecycle", "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("fragment lifecycle", "onDetach")
    }
}
