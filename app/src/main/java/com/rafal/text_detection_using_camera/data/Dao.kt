package com.rafal.text_detection_using_camera.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rafal.text_detection_using_camera.utils.Constants
import kotlinx.coroutines.flow.Flow


@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insert(data: ImageEntity)

    @Query("SELECT * FROM ${Constants.TABLE_NAME} ORDER BY id ASC")
    fun readData(): Flow<List<ImageEntity>>



}