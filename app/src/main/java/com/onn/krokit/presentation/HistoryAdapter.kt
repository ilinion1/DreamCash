package com.onn.krokit.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.onn.krokit.R

class HistoryAdapter: RecyclerView.Adapter<HistoryAdapter.HistoryHolder>() {
    val listImage = arrayListOf<Int>()

    class HistoryHolder(view: View): RecyclerView.ViewHolder(view) {
        val imageMain = view.findViewById<ImageView>(R.id.imMain)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return HistoryHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryHolder, position: Int) {
        holder.imageMain.setImageResource(listImage[position])
    }

    override fun getItemCount(): Int {
        return listImage.size
    }
}