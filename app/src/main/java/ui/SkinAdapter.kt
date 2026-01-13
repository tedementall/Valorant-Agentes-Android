package com.example.valorantapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.valorantapp.R
import com.example.valorantapp.data.Skin

class SkinAdapter(
    private var skinList: List<Skin>,
    private val onSkinClicked: (Skin) -> Unit
) : RecyclerView.Adapter<SkinAdapter.SkinViewHolder>() {

    class SkinViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val skinIcon: ImageView = itemView.findViewById(R.id.ivSkinIcon)
        val skinName: TextView = itemView.findViewById(R.id.tvSkinName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkinViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_skin, parent, false)
        return SkinViewHolder(view)
    }

    override fun onBindViewHolder(holder: SkinViewHolder, position: Int) {
        val skin = skinList[position]
        holder.skinName.text = skin.displayName
        Glide.with(holder.itemView.context).load(skin.displayIcon).into(holder.skinIcon)
        holder.itemView.setOnClickListener { onSkinClicked(skin) }
    }

    override fun getItemCount() = skinList.size

    fun updateList(newList: List<Skin>) {
        skinList = newList
        notifyDataSetChanged()
    }
}