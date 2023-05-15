package com.example.hikingdom.ui.socket

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.target.CustomTarget
import com.example.hikingdom.R
import com.example.hikingdom.databinding.ActivitySocketBinding
import com.example.hikingdom.ui.BaseActivity
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import java.security.MessageDigest


class SocketActivity: BaseActivity<ActivitySocketBinding>(ActivitySocketBinding::inflate){
    override fun initAfterBinding() {
        // 지도 띄우기
        val mapView = MapView(this)
        val mapViewContainer = binding.mapView
        mapViewContainer.addView(mapView)

        // 지도 중심 잡기
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.53737528, 127.00557633), true);

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
            .transform(CenterCrop(), WhiteBorderTransformation(5))
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                ) {
                    customMarker.customImageBitmap = resource
                    mapView.addPOIItem(customMarker)
                }
                override fun onLoadCleared(placeholder: Drawable?) {
                    customMarker.customImageResourceId = R.drawable.custom_marker_red
                    mapView.addPOIItem(customMarker)
                }
            })

    }

    class WhiteBorderTransformation(private val borderWidth: Int) : BitmapTransformation() {

        override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
            val size = Math.min(toTransform.width, toTransform.height)
            val x = (toTransform.width - size) / 2
            val y = (toTransform.height - size) / 2

            var bitmap = pool.get(size, size, Bitmap.Config.ARGB_8888)
            if (bitmap == null) {
                bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
            }

            val canvas = Canvas(bitmap)
            val paint = Paint()
            paint.isAntiAlias = true

            // 원본 이미지를 그립니다.
            canvas.drawBitmap(toTransform, x.toFloat(), y.toFloat(), paint)

            // 흰색 보더를 그립니다.
            paint.style = Paint.Style.STROKE
            paint.color = Color.WHITE
            paint.strokeWidth = borderWidth.toFloat() * toTransform.density

            canvas.drawRect(
                x.toFloat(),
                y.toFloat(),
                (x + size).toFloat(),
                (y + size).toFloat(),
                paint
            )

            return bitmap
        }

        override fun updateDiskCacheKey(messageDigest: MessageDigest) {
            messageDigest.update(ID_BYTES)
        }

        override fun hashCode(): Int {
            return ID.hashCode()
        }

        override fun equals(other: Any?): Boolean {
            return other is WhiteBorderTransformation
        }

        companion object {
            private const val ID = "com.example.WhiteBorderTransformation"
            private val ID_BYTES: ByteArray = ID.toByteArray(Charsets.UTF_8)
        }
    }
}