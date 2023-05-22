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
import java.nio.ByteBuffer
import java.security.MessageDigest

class BorderWithTriangleTransformation : BitmapTransformation() {

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        val size = 230 // 원의 크기
        val paintWidth = 40 // 테두리의 너비
        val triangleSize = 40 // 역삼각형의 크기

        val bitmap = pool.get(size, size, Bitmap.Config.ARGB_8888) // 32비트 ARGB 형식의 정사각형 비트맵
        val canvas = Canvas(bitmap)
        val paint = Paint().apply {
            isAntiAlias = true
        }

        // 이미지를 원 형태로 크롭
        val shader = BitmapShader(toTransform, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.style = Paint.Style.FILL
        paint.shader = shader
        canvas.drawCircle(size / 2f, size / 2f, size / 2f - paintWidth , paint)

        // 흰색 테두리 그리기
        paint.style = Paint.Style.STROKE
        paint.color = Color.WHITE
        paint.shader = null
        paint.strokeWidth = paintWidth.toFloat() * toTransform.density
        canvas.drawCircle(size / 2f, size / 2f, size / 2f + paintWidth , paint)

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

        return bitmap
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
    }

    override fun hashCode(): Int {
        return ID.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return other is BorderWithTriangleTransformation
    }

    companion object {
        private const val ID = "com.example.CircleCropWithTriangleTransformation"
        private val ID_BYTES: ByteArray = ID.toByteArray(Charsets.UTF_8)
    }
}

class BorderTransformation(
    private val borderWidth: Int,
    private val borderColor: Int
) : BitmapTransformation() {

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        val width = toTransform.width
        val height = toTransform.height

        val bitmap = pool.get(width, height, Bitmap.Config.ARGB_8888)
            ?: Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        val canvas = Canvas(bitmap)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = borderWidth.toFloat()
        paint.color = borderColor

        canvas.drawBitmap(toTransform, 0f, 0f, null)
        canvas.drawCircle(
            width / 2f,
            height / 2f,
            Math.min(width, height) / 2f - borderWidth / 2f,
            paint
        )

        return bitmap
    }

    override fun equals(other: Any?): Boolean {
        return other is BorderTransformation &&
                borderWidth == other.borderWidth &&
                borderColor == other.borderColor
    }

    override fun hashCode(): Int {
        return ID.hashCode() + borderWidth * 100 + borderColor
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
        messageDigest.update(ByteBuffer.allocate(4).putInt(borderWidth).array())
        messageDigest.update(ByteBuffer.allocate(4).putInt(borderColor).array())
    }

    companion object {
        private const val ID = "BorderTransformation"
        private val ID_BYTES = ID.toByteArray(Charsets.UTF_8)
    }
}

class TriangleTransformation(
    private val triangleSize: Int,
    private val triangleColor: Int
) : BitmapTransformation() {

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        val width = toTransform.width
        val height = toTransform.height

        val bitmap = pool.get(width, height + triangleSize, Bitmap.Config.ARGB_8888)
            ?: Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        val canvas = Canvas(bitmap)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.FILL
        paint.color = triangleColor

        canvas.drawBitmap(toTransform, 0f, 0f, null)

        val path = Path()
        val triangleLeft = (width - triangleSize) / 2f
        val triangleRight = (width + triangleSize) / 2f
        val triangleTop = height.toFloat()
        path.moveTo(triangleLeft, triangleTop)
        path.lineTo(triangleRight, triangleTop)
        path.lineTo(width / 2f, triangleTop + triangleSize.toFloat())
        path.close()

        canvas.drawPath(path, paint)

        return bitmap
    }

    override fun equals(other: Any?): Boolean {
        return other is TriangleTransformation &&
                triangleSize == other.triangleSize &&
                triangleColor == other.triangleColor
    }

    override fun hashCode(): Int {
        return ID.hashCode() + triangleSize * 100 + triangleColor
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
        messageDigest.update(ByteBuffer.allocate(4).putInt(triangleSize).array())
        messageDigest.update(ByteBuffer.allocate(4).putInt(triangleColor).array())
    }

    companion object {
        private const val ID = "TriangleTransformation"
        private val ID_BYTES = ID.toByteArray(Charsets.UTF_8)
    }
}