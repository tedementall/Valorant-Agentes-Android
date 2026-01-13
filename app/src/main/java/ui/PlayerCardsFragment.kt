package com.example.valorantapp.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.valorantapp.R
import com.example.valorantapp.data.PlayerCard
import com.example.valorantapp.data.RetrofitClient
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class PlayerCardsFragment : Fragment() {

    private lateinit var cardAdapter: PlayerCardAdapter
    private var allCards: List<PlayerCard> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_player_cards, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvPlayerCards)
        val searchInput = view.findViewById<TextInputEditText>(R.id.etPlayerCardName)

        cardAdapter = PlayerCardAdapter(emptyList()) { card ->
            card.uuid?.let { uuid ->
                val action = PlayerCardsFragmentDirections.actionNavPlayerCardsToPlayerCardDetailFragment(uuid)
                findNavController().navigate(action)
            }
        }
        recyclerView.adapter = cardAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.getPlayerCards("es-MX")
                allCards = response.data ?: emptyList()
                cardAdapter.updateList(allCards)
            } catch (e: Exception) {
                Log.e("PlayerCardsFragment", "Error al cargar las tarjetas", e)
            }
        }

        searchInput.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val filteredList = allCards.filter {
                    it.displayName?.contains(s.toString(), ignoreCase = true) == true
                }
                cardAdapter.updateList(filteredList)
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }
}