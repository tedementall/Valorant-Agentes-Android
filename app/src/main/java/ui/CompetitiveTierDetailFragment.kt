package com.example.valorantapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.valorantapp.R
import com.example.valorantapp.data.RetrofitClient
import kotlinx.coroutines.launch

class CompetitiveTierDetailFragment : Fragment() {

    private val args: CompetitiveTierDetailFragmentArgs by navArgs()
    private lateinit var rankAdapter: RankAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_competitivetier_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvRanks)
        rankAdapter = RankAdapter(emptyList())
        recyclerView.adapter = rankAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.getCompetitiveTiers()
                val episode = response.data?.find { it.uuid == args.episodeUuid }

                // La API devuelve los rangos de Radiante a Hierro, así que los invertimos
                val ranks = episode?.tiers?.reversed() ?: emptyList()
                rankAdapter.updateList(ranks)
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }
}