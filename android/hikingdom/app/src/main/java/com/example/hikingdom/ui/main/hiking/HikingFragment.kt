package com.example.hikingdom.ui.main.hiking

//import com.example.hikingdom.ui.main.group.GroupFragment

import android.Manifest
import android.app.AlertDialog
import android.content.*
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.webkit.WebView
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.example.hikingdom.ApplicationClass.Companion.BASE_URL
import com.example.hikingdom.BuildConfig
import com.example.hikingdom.R
import com.example.hikingdom.data.remote.api.RetrofitTokenInstance
import com.example.hikingdom.data.remote.hiking.HikingRetrofitInterface
import com.example.hikingdom.data.remote.hiking.MeetupResponse
import com.example.hikingdom.data.remote.hiking.MountainResponse
import com.example.hikingdom.data.remote.hiking.SocketEnterData
import com.example.hikingdom.data.remote.hiking.SocketGPSData
import com.example.hikingdom.databinding.FragmentHikingBinding
import com.example.hikingdom.ui.BaseFragment
import com.example.hikingdom.ui.main.hiking.dialog.MeetupAdapter
import com.example.hikingdom.ui.main.hiking.dialog.MountainAdapter
import com.example.hikingdom.ui.main.mypage.MypageFragment
import com.example.hikingdom.utils.*
import com.google.gson.Gson
import io.socket.client.IO
import io.socket.client.Socket
import net.daum.mf.map.api.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.util.Timer
import java.util.TimerTask


class HikingFragment() : BaseFragment<FragmentHikingBinding>(FragmentHikingBinding::inflate)
//    , MapView.CurrentLocationEventListener
{
    private var locationService: LocationService? = null
    private val hikingViewModel: HikingViewModel by viewModels()

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

    private val LOCATION_PERMISSION_CODE = 1
    private val BACKGROUND_LOCATION_PERMISSION_CODE = 2

    lateinit var hikingStartBtn: Button
    lateinit var hikingFinishBtn: Button
    lateinit var hikingSummitBtn: Button

    private var mSocket: Socket? =null;
    private var nickname: String? = null
    private var profileUrl: String? = null
    private var level: Int? = null
    private var meetupId: Long? = null
    private var clubId: Long? = null
    private var memberId: Long? = null

    val gson: Gson = Gson()

    private val timer : Timer = Timer()
    var task : TimerTask? = null

    override fun initAfterBinding() {
        binding.lifecycleOwner = this
        binding.hikingFragmentViewModel = hikingViewModel

        mapView = MapView(requireContext())
        mapViewContainer = binding.hikingMapview
//        db = AppDatabase.getInstance(requireContext())!!    // 로컬 DB
        checkPermission()

    }


    companion object {
        fun newInstance(): HikingFragment = HikingFragment()
        const val ACTION_STOP = "${BuildConfig.APPLICATION_ID}.stop"
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

    fun setHikingService() {
        hikingStartBtn = binding.hikingStartBtn
        hikingFinishBtn = binding.hikingFinishBtn
        hikingSummitBtn = binding.hikingSummitBtn

//        if(locationService != null && locationService?.isHikingStarted?.value == true){
//            Log.d("setHikingService", "true")
//            hikingFinishBtn.visibility = View.VISIBLE
//            hikingStartBtn.visibility = View.GONE
//        } else{
//
//            Log.d("setHikingService", "false / "+locationService.toString() + "/ "+locationService?.isHikingStarted?.value)
//        }

        if (getIsLocationServiceRunning()) {  // 서비스를 실행중인지 여부를 LocationService에서 처리하여 sharedPreference에 저장해주고있다. 저장되어있는 값을 가져와서 실행중인지 확인 후 버튼을 띄워준다.
            showToast("등산 기록을 불러오는 중입니다.")

            // LocationService 이미 start된 상태이므로 binding만 해준다.
            bindHikingService()

            // 모임 하이킹 중일 경우, 웹소켓 연결
            if (getIsLocationServiceRunning() && getIsMeetup()) {
                connectSocket()
            }
            Log.d("Websocket condtion", getIsLocationServiceRunning().toString() + " " + getIsMeetup().toString())

            // 사용자의 실시간 위치 정보 화면에 띄워주기
            loadLocationInfo()
            hikingFinishBtn.visibility = View.VISIBLE
            hikingSummitBtn.visibility = View.VISIBLE
            hikingStartBtn.visibility = View.GONE
        } else {
            hikingFinishBtn.visibility = View.GONE
            hikingSummitBtn.visibility = View.GONE
            hikingStartBtn.visibility = View.VISIBLE
        }

        hikingStartBtn.setOnClickListener {
            // 모달 팝업
            if (hikingViewModel.isHikingStarted.value == false) {
                showSelectTypeDialog()
            }
        }

        hikingFinishBtn.setOnClickListener {
            finishHikingAndClearAll()
        }

        hikingSummitBtn.setOnClickListener {
            // 완등 인증 버튼을 누르면, 정상에 100m 이내일 시 인증완료. 인증 여부를 로컬에 저장
            if (getIsSummit()) {
                showToast("완등 인증이 완료되었습니다.")
                Log.d("saveIsSummit ", getIsSummit().toString() + "/ 이미 완등 인증을 완료")
            } else {
                if (checkIsSummit()) {
                    saveIsSummit(true)
                    showToast("완등 인증이 완료되었습니다.")
                    Log.d("saveIsSummit", getIsSummit().toString() + "/ 완등 인증 완료")
                } else {
                    saveIsSummit(false)
                    showToast("완등 인증이 완료되었습니다.")
                }
            }
        }
    }

    fun loadLocationInfo() {

        locationService?.totalDistance?.observe(this) {
            Log.d("HikingFragment", it.toString())
            hikingViewModel.setTotalDistance(it)
        }

        locationService?.duration?.observe(this) {
            hikingViewModel.setDuration(it)
        }

        locationService?.currentLocation?.observe(this) {
            hikingViewModel.setCurrentLocation(it)

            if (lastLat == 0.0 && lastLng == 0.0) {
                Log.d(
                    "polyline test 1",
                    StringBuilder("lastLat: ").append(lastLat.toString()).append("/ lastLng: ")
                        .append(lastLng.toString()).toString()
                )
                lastLat = it.latitude
                lastLng = it.longitude

                setDepartMarker(lastLat, lastLng)

                showToast("경로 기록을 시작합니다!")
            } else {
                val polyline = MapPolyline()
                Log.d(
                    "polyline test 2",
                    StringBuilder("lastLat: ").append(lastLat.toString()).append("/ lastLng: ")
                        .append(lastLng.toString()).append(lastLng.toString()).append("/ lat: ")
                        .append(it.latitude).append("/ lng: ").append(it.longitude).toString()
                )
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

        if (locationService?.isHikingStarted?.value == true) {
            Log.d("setHikingService", "true")
            hikingFinishBtn.visibility = View.VISIBLE
            hikingStartBtn.visibility = View.GONE
        } else {
            Log.d("setHikingService", "false")
        }

//        locationService?.isHikingFinished?.observe(this){
//            Log.d("isHikingFinished", it.toString())
//            if(it){
//                // 결과 모달창 띄우기
//                showHikingRecordResultDialog()
//
//                // 완상복구
//                hikingViewModel.isHikingFinished.value = false
//            }
//        }

        locationService?.hikingRecordId?.observe(this){
            Log.d("isHikingFinished", it.toString())
            if(it != 0L){
                hikingViewModel.hikingRecordId.value = it

                // 결과 모달창 띄우기
                showHikingRecordResultDialog()

                // 완상복구
                hikingViewModel.isHikingFinished.value = false
            }
        }
    }

    private fun checkPermission() {
//        Log.d("checkPermission", "첫번쨰 확인")
        if (ContextCompat.checkSelfPermission(
                this.requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Fine Location permission is granted
            // Check if current android version >= 11, if >= 11 check for Background Location permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (ContextCompat.checkSelfPermission(
                        this.requireActivity(), Manifest.permission.ACCESS_BACKGROUND_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    // Background Location Permission is granted so do your work here
//                    Log.d("checkPermission", "이미 백그라운드 허용됨")
                    setServiceAndMapView()
                } else {
                    // Ask for Background Location Permission
//                    Log.d("checkPermission", "이미 위치권한 허용됨, but 백그라운드 위치권한은 허용 X")
                    askPermissionForBackgroundUsage()
                }
            }
        } else {
            // Fine Location Permission is not granted so ask for permission
//            Log.d("checkPermission", "위치권한, 백그라운드 허용 X")
            askForLocationPermission()
        }
    }

    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            askPermissionForBackgroundUsage()
        } else {
            showToast("위치 권한을 허용하지 않으면 경로 기록 서비스를 이용하실 수 없습니다.")
        }
    }
    private val backgroundLocationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            setServiceAndMapView()
        } else {
            showToast("위치 권한을 항상 허용하지 않으면 백그라운드 경로 기록이 불가능합니다.")
        }
    }

    private fun askForLocationPermission() {
//        Log.d("askForLocationPermission", "진입")
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this.requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
//            Log.d("askForLocationPermission", "조건문 true")
            AlertDialog.Builder(this.requireContext()).setTitle("위치 권한 설정")
                .setMessage("경로 기록 서비스를 이용하려면 위치 권한을 허용해주세요.")
                .setPositiveButton("허용", DialogInterface.OnClickListener { dialog, which ->
                    ActivityCompat.requestPermissions(
                        this.requireActivity(), arrayOf<String>(
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ), LOCATION_PERMISSION_CODE
                    )
                }).setNegativeButton("취소", DialogInterface.OnClickListener { dialog, which ->
                    // Permission is denied by the user
                }).create().show()
        } else {
//            Log.d("askForLocationPermission", "조건문 false")
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun askPermissionForBackgroundUsage() {
//        Log.d("askPermissionForBackgroundUsage", "진입")
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this.requireActivity(), Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        ) {
//            Log.d("askPermissionForBackgroundUsage", "조건문 true")
            AlertDialog.Builder(this.requireContext()).setTitle("백그라운드 권한 설정")
                .setMessage("백그라운드 위치 권한을 설정하지 않으면, 백그라운드 경로 기록 서비스를 이용할 수 없어요.")
                .setPositiveButton("설정", DialogInterface.OnClickListener { dialog, which ->
                    ActivityCompat.requestPermissions(
                        this.requireActivity(), arrayOf<String>(
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION
                        ), BACKGROUND_LOCATION_PERMISSION_CODE
                    )
                }).setNegativeButton("취소", DialogInterface.OnClickListener { dialog, which ->
                    // User declined for Background Location Permission.
                }).create().show()
        } else {
//            Log.d("askPermissionForBackgroundUsage", "조건문 false")
            backgroundLocationPermissionLauncher.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }
    }

    private fun setServiceAndMapView() {
        setHikingService()
        setMapView()
        if (getIsLocationServiceRunning()) {
            Log.d("setServiceAndMapView", "locationService is not null")
            drawPolylineByExistingTrackingData()
        } else {
            Log.d("setServiceAndMapView", "locationService is null")
        }
        setMarkerSetting()
    }

    fun startHikingService() {
        val startServiceIntent = Intent(activity, LocationService::class.java)
//            ContextCompat.startForegroundService(this.requireContext(), Intent(this.requireContext(), LocationService::class.java))
        startServiceIntent.putExtra("isMeetup", hikingViewModel.isMeetup.value)
        startServiceIntent.putExtra("meetupId", hikingViewModel.meetupId.value)
        startServiceIntent.putExtra("mountainId", hikingViewModel.mountainId.value)
        activity?.startService(startServiceIntent)
        Intent(activity, LocationService::class.java).also { intent ->
            activity?.bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    fun bindHikingService() {
        Intent(activity, LocationService::class.java).also { intent ->
            run {
                // 트래킹 정보 저장 API 호출 시 필요한 데이터들을 intent로 전달
                intent.putExtra("isMeetup", false)
                intent.putExtra("meetupId", 0L)
                intent.putExtra("mountainId", 0L)

                activity?.bindService(intent, connection, Context.BIND_AUTO_CREATE)
            }
        }
    }

    private fun setMapView() {
        mapViewContainer.addView(mapView)

//        Log.d("zoomLevel:",mapView.zoomLevelFloat.toString())
//        mapView.setZoomLevel(ZOOM_LEVEL, true);
//        mapView.isShowingCurrentLocationMarker
//        mapView.setCurrentLocationEventListener(this)
//        mapView.setMapViewEventListener(this)

        /* 현위치 트래킹 모드 및 나침반 모드를 설정한다.
        TrackingModeOnWithHeading: 현위치 트랙킹 모드 On + 나침반 모드 On, 단말의 위치에 따라 지도 중심이 이동하며 단말의 방향에 따라 지도가 회전한다. */
        mapView.currentLocationTrackingMode =
            MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading //
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

    private fun setMarkerSetting() {    // 마커를 찍기 위한 기본 세팅
        customMarker.itemName = "custom marker"
        customMarker.tag = 1
//        val mapPoint = MapPoint.mapPointWithGeoCoord(37.480426, 126.900177) //마커 표시할 위도경도
//        customMarker.mapPoint = MARKER_POINT
        customMarker.markerType = MapPOIItem.MarkerType.CustomImage // 마커타입을 커스텀 마커로 지정.

//        customMarker.customImageResourceId = R.drawable.custom_marker_red // 마커 이미지.

        customMarker.isCustomImageAutoscale =
            false // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.

        customMarker.setCustomImageAnchor(
            0.5f, 1.0f
        ) // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.
    }

    private fun drawPolylineByExistingTrackingData() {   // viewmodel은 앱이 백그라운드로 가면 같이 죽기 때문에 당연히 비어있음. 로컬에서 가져오도록 변경해야함.
        val polyline = MapPolyline()
        polyline.lineColor = POLYLINE_COLOR_CODE  // @color/blue 에 해당하는 rgb color

        val existingLocationList = db?.userLocationDao()?.getUserLocations()

        if (!existingLocationList.isNullOrEmpty()) {

            Log.d(
                "existingLocationList",
                existingLocationList.size.toString() + " / " + existingLocationList.toString()
            )

            val firstExistingLocation = existingLocationList[0]
            setDepartMarker(firstExistingLocation.latitude, firstExistingLocation.longitude)

            for (location in existingLocationList) {
                polyline.addPoint(
                    MapPoint.mapPointWithGeoCoord(
                        location.latitude, location.longitude
                    )
                )
            }
            polyline.lineColor = POLYLINE_COLOR_CODE  // @color/blue 에 해당하는 rgb color
            mapView.addPolyline(polyline)

            val lastExistingLocation = existingLocationList.last()
            lastLat = lastExistingLocation.latitude
            lastLng = lastExistingLocation.longitude

            // 지도뷰의 중심좌표와 줌레벨을 Polyline이 모두 나오도록 조정.
            // 지도뷰의 중심좌표와 줌레벨을 Polyline이 모두 나오도록 조정.
            val mapPointBounds = MapPointBounds(polyline.mapPoints)
            val padding = 300 // px

            mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding))
        } else {
            Log.d("existingLocationList", "is Null or Empty")
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

    private fun setDepartMarker(lat: Double, lng: Double) {
        // 출발 좌표에 마커 그리기
//        val firstExistingLocation = existingLocationList[0]
        val customMarker = MapPOIItem()
        customMarker.itemName = "depart"
        customMarker.tag = 1
        customMarker.mapPoint = MapPoint.mapPointWithGeoCoord(lat, lng)
        customMarker.markerType = MapPOIItem.MarkerType.CustomImage // 마커타입을 커스텀 마커로 지정.

        customMarker.customImageResourceId = R.drawable.custom_marker_red_25 // 마커 이미지.

        customMarker.isCustomImageAutoscale =
            false // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.

        customMarker.setCustomImageAnchor(
            0.7f, 1.0f
        ) // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.


        mapView.addPOIItem(customMarker)
    }

    private fun checkIsSummit(): Boolean {    // 현재 위치로 완등여부 확인
        val location = locationService?.currentLocation?.value
        if (location != null) {
            val summitLocation = Location("")
            // 뷰모델에 저장된 산 정상 좌표 가져오기
            val summitLat = hikingViewModel.mountainSummitLat.value
            val summitLng = hikingViewModel.mountainSummitLng.value
            Log.d("summit check", summitLat.toString() +" / " + summitLng.toString())
            if (!((summitLat == 0.0) or (summitLng == 0.0))) {
                summitLocation.latitude = summitLat!!
                summitLocation.longitude = summitLng!!

                val distance = summitLocation.distanceTo(location)
//            val distance = Location.distanceBetween(location.latitude, location.longitude, summitLocation.latitude, summitLocation.longitude)
                Log.d("distance", distance.toString())
                return distance < 200   // 200m 이내에 있으면 완등으로 판단
            } else {
                Log.d("checkIsSummit", "getMountainSummitLat() or getMountainSummitLng() is 0.0")
            }
        }
        return false
    }

    // dialog_select_hiking_type 띄우기
    private fun showSelectTypeDialog() {
        // dialog 띄우기
        val selectView =
            LayoutInflater.from(activityContext).inflate(R.layout.dialog_select_hiking_type, null)

        val dialog = AlertDialog.Builder(activityContext).create()
        dialog.setView(selectView)

        // 다이얼로그의 모서리를 둥글게 만들기
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.decorView?.setBackgroundResource(android.R.color.transparent)
        dialog.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.9).toInt(), // Set custom width as a percentage of the screen width
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setGravity(Gravity.CENTER) // Set dialog window gravity to center
        dialog.window?.attributes?.apply {
            flags = flags or WindowManager.LayoutParams.FLAG_DIM_BEHIND
            dimAmount = 0.6f
        }
        dialog.window?.setBackgroundDrawableResource(R.drawable.radius_10) // Set background image

        // TODO: Prevent dismissing on outside touch
        // dialog.setCancelable(false)

        dialog.show()

        // Click listener handles: meetup hiking, individual hiking
        val meetupHikingStart = selectView.findViewById<Button>(R.id.meetup_hiking_start_btn)
        val individualHikingStart =
            selectView.findViewById<Button>(R.id.individual_hiking_start_btn)

        meetupHikingStart.setOnClickListener {
            showMeetupDialog()
            dialog.dismiss()
        }
        individualHikingStart.setOnClickListener {
            showMountainDialog()
            dialog.dismiss()
        }
    }

    private fun showMeetupDialog() {
        // dialog 띄우기
        val meetupView =
            LayoutInflater.from(activityContext).inflate(R.layout.dialog_select_meetup, null)

        val meetupDialog = AlertDialog.Builder(activityContext).create();
        meetupDialog.setView(meetupView)
//        mBuilder.setCancelable(false) // 바깥 터치시 dialog 닫히는 것을 방지

        meetupDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        meetupDialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        meetupDialog.window?.decorView?.setBackgroundResource(android.R.color.transparent)
        meetupDialog.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.9).toInt(), // Set custom width as a percentage of the screen width
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        meetupDialog.window?.setGravity(Gravity.CENTER) // Set dialog window gravity to center
        meetupDialog.window?.attributes?.apply {
            flags = flags or WindowManager.LayoutParams.FLAG_DIM_BEHIND
            dimAmount = 0.6f
        }
        meetupDialog.window?.setBackgroundDrawableResource(R.drawable.radius_10) // Set background image
        meetupDialog.show()

        // 오늘 일정 없을 때 뜨는 뷰 GONE으로 초기화
        meetupDialog.findViewById<ImageView>(R.id.dialog_meetup_no_meetup_iv).visibility = View.GONE
        meetupDialog.findViewById<TextView>(R.id.dialog_meetup_no_meetup_tv).visibility = View.GONE

        meetupDialog.findViewById<TextView>(R.id.to_agreement).setOnClickListener {
            showLocationSharingAgreementDialog()
        }

        // 데이터 불러오기
        val api = RetrofitTokenInstance.getInstance().create(HikingRetrofitInterface::class.java)
        api.getTodayMeetups().enqueue(object : Callback<MeetupResponse> {
            override fun onResponse(
                call: Call<MeetupResponse>, response: Response<MeetupResponse>
            ) {
                val rv = meetupDialog.findViewById<RecyclerView>(R.id.recycler_view_meetup)
                Log.d("meetup!", "${response.body()}")
                val meetupList = response.body()!!.result
                if(meetupList.isNotEmpty()){
                    val meetupAdapter = MeetupAdapter(activityContext, meetupList)
                    rv.adapter = meetupAdapter
                    rv.layoutManager = LinearLayoutManager(activityContext)

                    // adapter 클릭 리스너 등록
                    meetupAdapter.setItemClickListener(object : MeetupAdapter.OnItemClickListener {
                        override fun onClick(v: View, position: Int) {
                            // viewModel에 데이터 저장

                            Log.d("meetupList mountainSummitLatLng", meetupList[position].mountainSummitLat.toString() + " / " + meetupList[position].mountainSummitLng.toString() )
                            hikingViewModel.isMeetup.value = true
                            hikingViewModel.meetupId.value = meetupList[position].meetupId
                            hikingViewModel.mountainId.value = meetupList[position].mountainId
                            hikingViewModel.mountainName.value = meetupList[position].mountainName
                            hikingViewModel.mountainSummitLat.value =
                                meetupList[position].mountainSummitLat
                            hikingViewModel.mountainSummitLng.value =
                                meetupList[position].mountainSummitLng

                            // 클릭 시 이벤트
                            val agreementRadioButton =
                                meetupView.findViewById<RadioButton>(R.id.radiobutton_polish)
                            if (agreementRadioButton.isChecked) {
                                showAgreementHikingMeetupModal()
                                meetupDialog.dismiss()
                            } else {
                                Toast.makeText(context, "약관에 동의 후 진행해주세요", Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
                } else {
                    meetupDialog.findViewById<ImageView>(R.id.dialog_meetup_no_meetup_iv).visibility = View.VISIBLE
                    meetupDialog.findViewById<TextView>(R.id.dialog_meetup_no_meetup_tv).visibility = View.VISIBLE
                }
            }


            override fun onFailure(call: Call<MeetupResponse>, t: Throwable) {
                Log.d("user!", "fail")
            }
        })
    }

    private fun showMountainDialog() {
        // 다이얼로그창 띄우기
        val mountainView =
            LayoutInflater.from(activityContext).inflate(R.layout.dialog_select_mountain, null)

        val mountainDialog = AlertDialog.Builder(activityContext).create()
        mountainDialog.setView(mountainView)

        // 다이얼로그의 모서리를 둥글게 만들기
        mountainDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        mountainDialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        mountainDialog.window?.decorView?.setBackgroundResource(android.R.color.transparent)
        mountainDialog.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.9).toInt(), // Set custom width as a percentage of the screen width
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        mountainDialog.window?.setGravity(Gravity.CENTER) // Set dialog window gravity to center
        mountainDialog.window?.attributes?.apply {
            flags = flags or WindowManager.LayoutParams.FLAG_DIM_BEHIND
            dimAmount = 0.6f
        }
        mountainDialog.window?.setBackgroundDrawableResource(R.drawable.radius_10) // Set background image

        mountainDialog.show()

        // 현재 위치 가져오기
        val locationInfo = LocationUtils.getCurrentLocation(activityContext)
        if (locationInfo == null) {
            Toast.makeText(activityContext, "위치 정보를 가져올 수 없습니다.", Toast.LENGTH_SHORT).show()
            return
        }
        val cur_lat = locationInfo[0]
        val cur_lng = locationInfo[1]

        // 데이터 불러오기
        val api = RetrofitTokenInstance.getInstance().create(HikingRetrofitInterface::class.java)
        api.getNearMountains(cur_lat, cur_lng).enqueue(object : Callback<MountainResponse> {
            override fun onResponse(
                call: Call<MountainResponse>, response: Response<MountainResponse>
            ) {
                Log.d("user!", "${response.body()}")
                val mountainList = response.body()!!.result
                val rv = mountainDialog.findViewById<RecyclerView>(R.id.mountain_rv)
                val mountainAdapter = MountainAdapter(activityContext, mountainList)
                rv.adapter = mountainAdapter
                rv.layoutManager = LinearLayoutManager(activityContext)

                // adapter 클릭 리스너 등록
                mountainAdapter.setItemClickListener(object : MountainAdapter.OnItemClickListener {
                    override fun onClick(v: View, position: Int) {
                        Log.d("mountainList mountainSummitLatLng", mountainList[position].mountainSummitLat.toString() + " / " + mountainList[position].mountainSummitLng.toString() )

                        // viewModel에 데이터 저장
                        hikingViewModel.mountainId.value = mountainList[position].mountainId
                        hikingViewModel.mountainName.value = mountainList[position].name
                        hikingViewModel.mountainSummitLat.value =
                            mountainList[position].mountainSummitLat
                        hikingViewModel.mountainSummitLng.value =
                            mountainList[position].mountainSummitLng

                        // 클릭 시 이벤트
                        showAgreementHikingMeetupModal()
                        mountainDialog.dismiss()
                    }
                })
            }

            override fun onFailure(call: Call<MountainResponse>, t: Throwable) {
                Log.d("user!", "fail")
            }
        })
    }

    private fun showLocationSharingAgreementDialog() {
        // dialog 띄우기
        val meetupView =
            LayoutInflater.from(activityContext).inflate(R.layout.dialog_detail_agreement, null)
        val mBuilder = AlertDialog.Builder(activityContext).setView(meetupView)
//        mBuilder.setCancelable(false) // 바깥 터치시 dialog 닫히는 것을 방지
        val locationAgreementDialog = mBuilder.show()

        // 뒤로가기 버튼을 눌렀을 때 동작
        locationAgreementDialog.findViewById<Button>(R.id.back_btn).setOnClickListener {
            locationAgreementDialog.dismiss()
        }
    }

    private fun showAgreementHikingMeetupModal() {  // 선 선택 혹은 일정 선택 후 하이킹 시작 전 최종으로 뜨는 모달
        // dialog 띄우기
        val selectView =
            LayoutInflater.from(activityContext).inflate(R.layout.dialog_hiking_agreement, null)
        selectView.findViewById<TextView>(R.id.selected_hiking_mountain).text =
            hikingViewModel.mountainName.value

        val dialog = AlertDialog.Builder(activityContext).create()
        dialog.setView(selectView);

        // 다이얼로그의 모서리를 둥글게 만들기
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.decorView?.setBackgroundResource(android.R.color.transparent)
        dialog.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.9).toInt(), // Set custom width as a percentage of the screen width
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setGravity(Gravity.CENTER) // Set dialog window gravity to center
        dialog.window?.attributes?.apply {
            flags = flags or WindowManager.LayoutParams.FLAG_DIM_BEHIND
            dimAmount = 0.6f
        }
        dialog.window?.setBackgroundDrawableResource(R.drawable.radius_10) // Set background image

        dialog.show()

        // 클릭 리스너 핸들 -> 확인
        val hikingStart = selectView.findViewById<Button>(R.id.hiking_mountain_start_btn)
        hikingStart.setOnClickListener { dialog.dismiss(); showToast("출발 마커가 찍힐 때까지 잠시 기다려 주세요."); startHiking();}

        // 클릭 리스너 핸들 -> 취소
        val hikingCancel = selectView.findViewById<Button>(R.id.hiking_mountain_cancel_start_btn)
        hikingCancel.setOnClickListener { dialog.dismiss();hikingViewModel.meetupClear(); showSelectTypeDialog() }
    }

    fun startHiking(){
        startHikingService()

        Log.d("CONDITON", hikingViewModel.isHikingStarted.value.toString() + " " + hikingViewModel.isMeetup.value.toString())
        // 모임 하이킹 중일 경우, 웹소켓 연결
        if (hikingViewModel.isMeetup.value == true) {
            connectSocket()
        }   // 확인 시 바로 하이킹 시작됨 (인증하기 버튼, 하이킹 종료 버튼으로 바뀜)

        hikingFinishBtn.visibility = View.VISIBLE
        hikingSummitBtn.visibility = View.VISIBLE
        hikingStartBtn.visibility = View.GONE

        saveIsSummit(false) // 완등인증 여부 초기화

//        hikingViewModel.mountainSummitLat.value = 0.0  // 완등인증시 사용할 산 정상 위도 초기화
//        hikingViewModel.mountainSummitLng.value = 0.0  // 완등인증시 사용할 산 정상 경도 초기화

        // TODO: 산 목록 api, 오늘 일정 조회 api 호출, 호출하고 응답 잘 받아왔다면 산 정상의 lat, lng 같이 받아온다. 받아온 lat, lng viewmodel에 저장해야함
//            hikingViewModel.mountainSummitLat.value = 37.5013   // 임시 값 setting
//            hikingViewModel.mountainSummitLng.value = 127.0395  // 임시 값 setting
    }

    private fun connectSocket() {
        // 유저 정보 읽어오기
        val user = db?.userDao()?.getUser()
        nickname = user?.nickname
        profileUrl = user?.profileUrl
        level = user?.level
        memberId = user?.memberId
        clubId = user?.clubId
        meetupId = locationService?.meetupId

        Log.d("SOCKET1", "Connection success : " + mSocket)

        // 소켓 생성
        try {
            mSocket = IO.socket("http://hikingdom.kr:8003")
            Log.d("SOCKET3", "Connection success : " + mSocket)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("SOCKET", "소켓연결에 실패했습니다." )
        }

        // 소켓 구독 함수
        mSocket?.on(
            Socket.EVENT_CONNECT
        ) { args: Array<Any?>? ->
            Log.d("SOCKET5", "소켓 입장" )
            mSocket?.emit("enter", gson.toJson(SocketEnterData(nickname, memberId, meetupId))) // 입장 메시지 전송
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
        mSocket?.on("leave") { args: Array<Any> ->
            val data: SocketEnterData = gson.fromJson(
                args[0].toString(),
                SocketEnterData::class.java
            )
            onLeave(data)
        }

        // 소켓 연결
        mSocket?.connect()

        // 주기적으로 위치정보 소켓통신
        sendGPSPeriodically()
    }

    private fun sendGPSPeriodically() {
        task = object : TimerTask() {
            override fun run() {
                sendGPS()
            }
        }
        timer.schedule(task, 0, 20000)
    }

    private fun sendGPS() {

        // 현재 위치 정보 받아오기
        val locationInfo = LocationUtils.getCurrentLocation(requireContext())

        Log.d("sendLocation1", locationInfo.toString());

        if (locationInfo == null) {
            showToast("위치 정보를 받아오지 못했습니다.")
            return
        }

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
        activity?.runOnUiThread {
            // 데이터 추출
            val nickname = data.nickname
            val memberId = data.memberId
            val profileUrl = data.profileUrl
            val lat = data.lat
            val lng = data.lng

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

    private fun onLeave(data: SocketEnterData) {
        activity?.runOnUiThread {
            // 데이터 추출
            val nickname = data.nickname

            // 기존 마커 삭제
            val removeItems = mapView.findPOIItemByName(nickname)
            if (removeItems != null) {
                mapView.removePOIItems(removeItems)
            }
        }
    }

    private fun finishHikingAndClearAll(){
        val stopServiceIntent = Intent(activity, LocationService::class.java)
        stopServiceIntent.action = ACTION_STOP
        activity?.startService(stopServiceIntent)
        Intent(activity, LocationService::class.java).also { intent ->
            activity?.unbindService(connection)
        }
        bound = false
        hikingFinishBtn.visibility = View.GONE
        hikingSummitBtn.visibility = View.GONE
        hikingStartBtn.visibility = View.VISIBLE

        finishTime = LocalDateTime.now()    // 종료시간 세팅

        val removeItems = mapView.findPOIItemByName("depart")   // 출발 마커 삭제
        if (removeItems != null) {
            mapView.removePOIItems(removeItems)
        }
        mapView.removeAllPolylines()

        // 웹소켓 종료
        mSocket?.emit("leave", gson.toJson(SocketEnterData(nickname, memberId, meetupId)))
        mSocket?.disconnect();
        mSocket?.off("enter")
        mSocket?.off("newLocation")
        task?.cancel()
    }

    private fun showHikingRecordResultDialog() {
        // dialog 띄우기
        val selectView =
            LayoutInflater.from(activityContext).inflate(R.layout.dialog_hiking_record_result, null)

        val webView = selectView.findViewById<WebView>(R.id.finish_hiking_result_webview)

        val nickname = db?.userDao()?.getUser()?.nickname
//        val hikingRecordId = hikingViewModel.hikingRecordId.value
        val hikingRecordId = hikingViewModel.hikingRecordId.value
        webViewSetting(activityContext, webView, BASE_URL + "profile/"+ nickname + "/tracking/" + hikingRecordId)

        val mBuilder = AlertDialog.Builder(activityContext).setView(selectView)
        val hikingRecordResultDialog = mBuilder.show()

        // 클릭 리스너 핸들 -> 확인
        val hikingStart = selectView.findViewById<Button>(R.id.finish_hiking_result_confirm)
        hikingStart.setOnClickListener {
            hikingRecordResultDialog.dismiss()
            // MyPageFragment로 이동
            findNavController().navigate(R.id.action_hikingFragment_to_myPageFragment)

//            val fragment = MypageFragment()
//            val fragmentManager = activity?.supportFragmentManager
//            val fragmentTransaction = fragmentManager?.beginTransaction()
//            fragmentTransaction?.replace(R.id.nav_host_fragment_container, fragment)
////            fragmentTransaction?.addToBackStack(null)
//            fragmentTransaction?.commit()
        }
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
        Log.d(
            "onStop LocationList",
            locationService?.locations?.value?.size.toString() + " / " + locationService?.locations?.value.toString()
        )
        mSocket?.emit("leave", gson.toJson(SocketEnterData(nickname, memberId, meetupId)))
        mSocket?.disconnect();
        mSocket?.off("enter")
        mSocket?.off("newLocation")
        mSocket?.off("leave")
        task?.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("fragment lifecycle", "onDestroy")
        mapViewContainer?.removeAllViews()
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("fragment lifecycle", "onDetach")
    }
}
