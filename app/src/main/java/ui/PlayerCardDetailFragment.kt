package com.example.valorantapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.valorantapp.R
import com.example.valorantapp.data.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class PlayerCardDetailFragment : Fragment() {

    private val args: PlayerCardDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_player_card_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cardImage = view.findViewById<ImageView>(R.id.ivCardDetailImage)
        val cardTheme = view.findViewById<TextView>(R.id.tvCardTheme)
        cardTheme.visibility = View.GONE // Ocultamos el texto al principio

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val cardsDeferred = async { RetrofitClient.instance.getPlayerCards("es-MX") }
                val themesDeferred = async { RetrofitClient.instance.getThemes("es-MX") }

                val cardsResponse = cardsDeferred.await()
                val themesResponse = themesDeferred.await()

                val card = cardsResponse.data?.find { it.uuid == args.cardUuid }

                if (card != null) {
                    Glide.with(this@PlayerCardDetailFragment)
                        .load(card.largeArt)
                        .into(cardImage)

                    // Solo buscamos y mostramos el tema si la tarjeta tiene uno
                    if (card.themeUuid != null) {
                        val theme = themesResponse.data?.find { it.uuid == card.themeUuid }
                        cardTheme.text = "Origen: ${theme?.displayName ?: "Desconocido"}"
                        cardTheme.visibility = View.VISIBLE // Lo hacemos visible
                    }
                }
            } catch (e: Exception) {
                Log.e("PlayerCardDetail", "Error al cargar datos", e)
            }
        }
    }
}