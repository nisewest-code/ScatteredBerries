package com.scatt.eredbe.rries.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Keep
import androidx.recyclerview.widget.RecyclerView
import com.scatt.eredbe.rries.R

@Keep
class AdapterRv(val callback: (item: Int, pos: Int)->Unit): RecyclerView.Adapter<HolderImage>() {
    private var list = mutableListOf<Int>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderImage {
        return HolderImage(LayoutInflater.from(parent.context).inflate(R.layout.layout_holder_image, parent, false), callback)
    }

    override fun onBindViewHolder(holder: HolderImage, position: Int) {
        holder.updateView(list[position], position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(nList: List<Int>){
        list.clear()
        list.addAll(nList)
        notifyDataSetChanged()
    }

    fun updateItem(pos: Int){
        list.removeAt(pos)
        notifyDataSetChanged()
    }
}