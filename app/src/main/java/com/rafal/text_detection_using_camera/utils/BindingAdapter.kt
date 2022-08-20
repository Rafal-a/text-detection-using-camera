package com.rafal.text_detection_using_camera.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.room.TypeConverter
import com.rafal.text_detection_using_camera.R
import com.rafal.text_detection_using_camera.base.BaseAdapter
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream


@BindingAdapter(value = ["app:items"])
fun <T> setRecyclerItems(view: RecyclerView, items: List<T>?) {
    if (items != null) {
        (view.adapter as BaseAdapter<T>?)?.setItems(items)
    } else {
        (view.adapter as BaseAdapter<T>?)?.setItems(emptyList())
    }
}

@BindingAdapter(value = ["app:setImage"])
fun setImage(view: ImageView, item: String?) {
    Picasso.get()
        .load(item)
        .placeholder(R.drawable.ic_load_image)
        .error(R.drawable.ic_error_image)
        .into(view)
}
@BindingAdapter(value = ["app:set_Image"])
fun setImageToBitmap(view: ImageView, item: Bitmap?) {
    view.setImageBitmap(item)
}


