package com.example.valorantapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.valorantapp.R
import com.example.valorantapp.data.RetrofitClient
import kotlinx.coroutines.launch

class CompetitiveTiersFragment : Fragment() {

    private lateinit var rankAdapter: RankAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_competitive_tiers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvRanks)
        rankAdapter = RankAdapter(emptyList())
        recyclerView.adapter = rankAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.getCompetitiveTiers()
                // La API devuelve una lista de todos los episodios. Tomamos el último.
                val latestEpisode = response.data?.lastOrNull()

                if (latestEpisode != null) {
                    // La API devuelve los rangos de Radiante a Hierro, así que los invertimos
                    val ranks = latestEpisode.tiers?.reversed() ?: emptyList()
                    rankAdapter.updateList(ranks)
                }
            } catch (e: Exception) {
                Log.e("CompetitiveTiers", "Error al cargar los rangos", e)
            }
        }
    }
}