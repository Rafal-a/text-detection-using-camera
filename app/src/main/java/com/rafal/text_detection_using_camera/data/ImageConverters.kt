package com.rafal.text_detection_using_camera.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.text.TextUtils
import android.util.Base64
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream
import java.util.*


class ImageConverters {
    /*   @TypeConverter
       fun fromBitmap(bitmap: Bitmap): ByteArray {
           val outputStream = ByteArrayOutputStream()
           bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
           return outputStream.toByteArray()
       }

       @TypeConverter
       fun toBitmap(byteArray: ByteArray): Bitmap {
           return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
       }*/

    @TypeConverter
    fun fromBase64ToBitmap(base64Value: String?): Bitmap? {
        return if (!TextUtils.isEmpty(base64Value)) {
            val decodedBytes = Base64.decode(base64Value, 0)
            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        } else {
            null
        }
    }

    @TypeConverter
    fun fromBitmapToBase64(bitmap: Bitmap?): String? {
        return if (bitmap != null) {
            val byteArrayOS = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 25, byteArrayOS)
            Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT)
        } else {
            null
        }
    }

    @TypeConverter
    fun dateToLong(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun longToDate(long: Long): Date {
        return Date(long)
    }


}
