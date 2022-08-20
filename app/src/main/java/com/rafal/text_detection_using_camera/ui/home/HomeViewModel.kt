package com.rafal.text_detection_using_camera.ui.home

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import androidx.lifecycle.*
import com.rafal.text_detection_using_camera.data.ImageEntity
import com.rafal.text_detection_using_camera.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class HomeViewModel:ViewModel() {


    val repository = Repository()
    val scanText = MutableLiveData<String>()
    val image = MutableLiveData<Bitmap>()
    val data : LiveData<List<ImageEntity>> = repository.readAllData().asLiveData()


    fun insertData(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(
                ImageEntity(
                    0,
                    Date(),
                    scanText.value.toString(),
                    image.value
                )
            )
            scanText.postValue("")
            println(image.value)
        }
    }

    fun getBitmapFromView(view: View): Bitmap {
        return Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888).apply {
            Canvas(this).apply {
                view.draw(this)
            }
        }
    }



}