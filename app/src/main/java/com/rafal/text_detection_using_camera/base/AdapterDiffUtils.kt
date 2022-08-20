package com.rafal.text_detection_using_camera.base

import androidx.recyclerview.widget.DiffUtil

class AdapterDiffUtils<T>(val oldList: List<T>, val newList: List<T>) :
    DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = true
}