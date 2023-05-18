package com.scatt.eredbe.rries.rv

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class HolderImage(itemView: View, val callback: (item: Int, pos: Int)->Unit) : RecyclerView.ViewHolder(itemView) {

    fun updateView(item: Int, pos: Int){
        (itemView as ImageView).setImageResource(item)
        itemView.setOnClickListener {
            callback(item, pos)
        }
    }
}