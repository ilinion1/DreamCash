package com.onn.krokit.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.onn.krokit.R

class GameAdapter(activity: FragmentActivity): RecyclerView.Adapter<GameAdapter.GameHolder>() {
    val listImage = arrayListOf<Int>()
    val viewModel by lazy { ViewModelProvider(activity)[GameViewModel::class.java] }

    class GameHolder(view: View): RecyclerView.ViewHolder(view) {
        val imageMain = view.findViewById<ImageView>(R.id.imGameItem)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.game_item, parent, false)
        return GameHolder(view)
    }

    override fun onBindViewHolder(holder: GameHolder, position: Int) {
        holder.imageMain.setImageResource(listImage[position])
        viewModel.imagePosition = position
    }

    override fun getItemCount(): Int {
        return listImage.size
    }
}