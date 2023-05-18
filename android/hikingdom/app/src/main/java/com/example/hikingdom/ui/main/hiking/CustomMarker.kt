package com.example.hikingdom.ui.main.hiking

import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Shader
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest

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
