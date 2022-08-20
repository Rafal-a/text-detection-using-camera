package com.rafal.text_detection_using_camera.ui.history

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rafal.text_detection_using_camera.R
import com.rafal.text_detection_using_camera.data.ImageEntity
import com.rafal.text_detection_using_camera.databinding.ItemHistoryDataBinding
import java.io.ByteArrayOutputStream

class Adapter(private var list: List<ImageEntity>): RecyclerView.Adapter<Adapter.MatchViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        //provide the necessary items to show
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_history_data, parent, false)
        return MatchViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        //list that has the current position
        val currentValue = list[position]

        holder.binding.apply {
            textScan.text = currentValue.Text
          //  imageScan.setImageBitmap(BitmapFactory.decodeByteArray(converter(currentValue.image),0 ,currentValue.image.byteCount))
        }
    }

    private fun converter(bitmap: Bitmap): ByteArray{
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }


    override fun getItemCount() = list.size

    class MatchViewHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem) {
        val binding = ItemHistoryDataBinding.bind(viewItem)
    }
}