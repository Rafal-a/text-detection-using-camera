package com.rafal.text_detection_using_camera.ui.history

import androidx.lifecycle.*
import com.rafal.text_detection_using_camera.data.Repository
import com.rafal.text_detection_using_camera.data.ImageEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel() : ViewModel() {

    val test = MutableLiveData<List<ImageEntity>>()
     val repository = Repository()

    val readImage: LiveData<List<ImageEntity>> = repository.readAllData().asLiveData()
    private val _data = MutableLiveData<List<ImageEntity>>()
    val data : LiveData<List<ImageEntity>> = _data


    init {
        loadNotes()
    }
    fun loadNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.readAllData().collect{
                _data.postValue(it)
            }
        }
    }

}