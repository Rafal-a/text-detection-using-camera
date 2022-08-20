package com.rafal.text_detection_using_camera.ui.history

import com.rafal.text_detection_using_camera.R
import com.rafal.text_detection_using_camera.base.BaseAdapter
import com.rafal.text_detection_using_camera.data.ImageEntity

class HistoryAdapter( val item: List<ImageEntity>): BaseAdapter<ImageEntity>(item){
    override val layoutId: Int
        get() = R.layout.item_history_data

}