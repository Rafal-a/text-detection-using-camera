package com.rafal.text_detection_using_camera.data

import androidx.lifecycle.LiveData
import com.rafal.text_detection_using_camera.data.Dao
import com.rafal.text_detection_using_camera.data.ImageEntity

class Repository() {

    private val dao = ImageDatabase.getInstanceWithoutContext().getDao()
    suspend fun insertData(imageEntity: ImageEntity) = dao.insert(imageEntity)


     fun readAllData()=dao.readData()

}