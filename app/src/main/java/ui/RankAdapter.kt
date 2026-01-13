package com.example.valorantapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.valorantapp.R
import com.example.valorantapp.data.Tier

class RankAdapter(private var ranks: List<Tier>) : RecyclerView.Adapter<RankAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: ImageView = view.findViewById(R.id.ivRankIcon)
        val name: TextView = view.findViewById(R.id.tvRankName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_rank, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rank = ranks[position]
        // Ocultamos los rangos no usados o sin nombre
        if (rank.tierName == null || rank.tierName.contains("Unused")) {
            holder.itemView.visibility = View.GONE
            holder.itemView.layoutParams = RecyclerView.LayoutParams(0, 0)
            return
        }
        holder.itemView.visibility = View.VISIBLE
        holder.itemView.layoutParams = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        holder.name.text = rank.tierName
        Glide.with(holder.itemView.context).load(rank.largeIcon).into(holder.icon)
    }

    override fun getItemCount() = ranks.size

    fun updateList(newRanks: List<Tier>) {
        ranks = newRanks
        notifyDataSetChanged()
    }
}