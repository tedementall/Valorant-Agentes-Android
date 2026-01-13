package com.example.valorantapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.valorantapp.R
import com.example.valorantapp.data.CompetitiveTierEpisode

class CompetitiveTierAdapter(
    private var episodes: List<CompetitiveTierEpisode>,
    private val onEpisodeClicked: (CompetitiveTierEpisode) -> Unit
) : RecyclerView.Adapter<CompetitiveTierAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tvEpisodeName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_episode, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val episode = episodes[position]

        // --- ESTA ES LA LÓGICA CORREGIDA ---
        val rawName = episode.assetObjectName ?: ""
        // Extraemos solo el número del episodio (ej: "1" de "Episode1_...")
        val episodeNumber = rawName.removePrefix("Episode").substringBefore("_")

        holder.name.text = "Episodio $episodeNumber"

        holder.itemView.setOnClickListener { onEpisodeClicked(episode) }
    }

    override fun getItemCount() = episodes.size

    fun updateList(newEpisodes: List<CompetitiveTierEpisode>) {
        episodes = newEpisodes
        notifyDataSetChanged()
    }
}