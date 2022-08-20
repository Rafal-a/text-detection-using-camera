package com.rafal.text_detection_using_camera.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rafal.text_detection_using_camera.BR

abstract class BaseAdapter<T>(
    private var items: List<T>,
) : RecyclerView.Adapter<BaseAdapter.BaseViewHolder>() {

    abstract val layoutId : Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return ItemViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                layoutId,
                parent,
                false
            ))
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val currentItem = items[position]
        when(holder) {
            is ItemViewHolder ->{
                holder.binding.setVariable(BR.item, items[position])
            }
        }
    }

    fun setItems(newItems : List<T>){
        val moviesDiffUtil = DiffUtil.calculateDiff(AdapterDiffUtils(items, newItems))
        items = newItems
        moviesDiffUtil.dispatchUpdatesTo(this)

    }

    fun getItems() = items

    override fun getItemCount() = items.size

    abstract class BaseViewHolder(binding : ViewDataBinding) : RecyclerView.ViewHolder(binding.root)

    class ItemViewHolder(val binding: ViewDataBinding) : BaseViewHolder(binding)


}