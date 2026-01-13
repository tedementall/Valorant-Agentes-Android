package com.example.valorantapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.valorantapp.R
import com.example.valorantapp.data.PlayerCard

class PlayerCardAdapter(
    private var cardList: List<PlayerCard>,
    private val onCardClicked: (PlayerCard) -> Unit // Añadimos el listener
) : RecyclerView.Adapter<PlayerCardAdapter.PlayerCardViewHolder>() {

    class PlayerCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardImage: ImageView = itemView.findViewById(R.id.ivPlayerCardImage)
        val cardName: TextView = itemView.findViewById(R.id.tvPlayerCardName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerCardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_player_card, parent, false)
        return PlayerCardViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerCardViewHolder, position: Int) {
        val card = cardList[position]
        holder.cardName.text = card.displayName

        Glide.with(holder.itemView.context)
            .load(card.wideArt)
            .into(holder.cardImage)

        // Hacemos la fila clickeable
        holder.itemView.setOnClickListener {
            onCardClicked(card)
        }
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    fun updateList(newList: List<PlayerCard>) {
        cardList = newList
        notifyDataSetChanged()
    }
}