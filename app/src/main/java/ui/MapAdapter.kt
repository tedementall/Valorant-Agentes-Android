package com.example.valorantapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.valorantapp.R
import com.example.valorantapp.data.MapData

class MapAdapter(
    private var mapList: List<MapData>,
    private val onMapClicked: (MapData) -> Unit
) : RecyclerView.Adapter<MapAdapter.MapViewHolder>() {

    class MapViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mapImage: ImageView = itemView.findViewById(R.id.ivMapImage)
        val mapName: TextView = itemView.findViewById(R.id.tvMapName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_map, parent, false)
        return MapViewHolder(view)
    }

    override fun onBindViewHolder(holder: MapViewHolder, position: Int) {
        val map = mapList[position]
        holder.mapName.text = map.displayName

        Glide.with(holder.itemView.context)
            .load(map.splash)
            .into(holder.mapImage)

        holder.itemView.setOnClickListener {
            onMapClicked(map)
        }
    }

    override fun getItemCount(): Int {
        return mapList.size
    }

    fun updateList(newList: List<MapData>) {
        mapList = newList
        notifyDataSetChanged()
    }
}