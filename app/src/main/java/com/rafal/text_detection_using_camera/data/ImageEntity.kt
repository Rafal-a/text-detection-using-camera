package com.rafal.text_detection_using_camera.data

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rafal.text_detection_using_camera.utils.Constants
import java.util.*

@Entity(tableName = Constants.TABLE_NAME)
data class ImageEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val date: Date,
    val Text: String?,
    val image: Bitmap?,

    ) {
    // @ColumnInfo(typeAffinity = ColumnInfo.BLOB) val image:Bitmap,

}