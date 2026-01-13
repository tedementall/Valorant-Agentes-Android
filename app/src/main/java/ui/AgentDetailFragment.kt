package com.example.valorantapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.valorantapp.R
import com.example.valorantapp.data.RetrofitClient
import kotlinx.coroutines.launch

class AgentDetailFragment : Fragment() {

    private val args: AgentDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_agent_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(bottom = systemBars.bottom)
            insets
        }

        val agentIcon = view.findViewById<ImageView>(R.id.ivAgentDetailIcon)
        val agentName = view.findViewById<TextView>(R.id.tvAgentDetailName)
        val agentDescription = view.findViewById<TextView>(R.id.tvAgentDetailDescription)
        val abilitiesContainer = view.findViewById<LinearLayout>(R.id.llAbilitiesContainer)

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.getAgents("es-MX", true)
                val agent = response.data?.find { it.uuid == args.agentUuid }

                if (agent != null) {
                    agentName.text = agent.displayName
                    agentDescription.text = agent.description

                    Glide.with(this@AgentDetailFragment)
                        .load(agent.fullPortrait)
                        .into(agentIcon)

                    abilitiesContainer.removeAllViews()
                    agent.abilities?.forEach { ability ->
                        val abilityView = layoutInflater.inflate(R.layout.list_item_ability, abilitiesContainer, false)
                        val abilityIcon = abilityView.findViewById<ImageView>(R.id.ivAbilityIcon)
                        val abilityName = abilityView.findViewById<TextView>(R.id.tvAbilityName)
                        val abilityDescription = abilityView.findViewById<TextView>(R.id.tvAbilityDescription)

                        abilityName.text = ability.displayName
                        abilityDescription.text = ability.description
                        Glide.with(this@AgentDetailFragment).load(ability.displayIcon).into(abilityIcon)

                        abilitiesContainer.addView(abilityView)
                    }
                }
            } catch (e: Exception) {
                Log.e("AgentDetailFragment", "Error al cargar detalles", e)
            }
        }
    }
}