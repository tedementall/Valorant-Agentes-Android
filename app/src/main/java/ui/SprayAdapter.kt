package com.example.valorantapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.valorantapp.R
import com.example.valorantapp.data.Spray

class SprayAdapter(private var sprayList: List<Spray>) : RecyclerView.Adapter<SprayAdapter.SprayViewHolder>() {

    class SprayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sprayIcon: ImageView = itemView.findViewById(R.id.ivSprayIconItem)
        val sprayName: TextView = itemView.findViewById(R.id.tvSprayNameItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SprayViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_spray, parent, false)
        return SprayViewHolder(view)
    }

    override fun onBindViewHolder(holder: SprayViewHolder, position: Int) {
        val spray = sprayList[position]
        holder.sprayName.text = spray.displayName

        Glide.with(holder.itemView.context)
            .load(spray.fullIcon)
            .into(holder.sprayIcon)
    }

    override fun getItemCount(): Int {
        return sprayList.size
    }

    fun updateList(newList: List<Spray>) {
        sprayList = newList
        notifyDataSetChanged()
    }
}