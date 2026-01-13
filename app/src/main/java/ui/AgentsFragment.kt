package com.example.valorantapp.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.valorantapp.R
import com.example.valorantapp.data.Agent
import com.example.valorantapp.data.RetrofitClient
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class AgentsFragment : Fragment() {

    private lateinit var agentAdapter: AgentAdapter
    private var allAgents: List<Agent> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_agents, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvAgents)
        val searchInput = view.findViewById<TextInputEditText>(R.id.etAgentName)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)

        ViewCompat.setOnApplyWindowInsetsListener(recyclerView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(bottom = systemBars.bottom)
            insets
        }

        agentAdapter = AgentAdapter(emptyList()) { agent ->
            agent.uuid?.let { uuid ->
                val action = AgentsFragmentDirections.actionNavAgentsToAgentDetailFragment(uuid)
                findNavController().navigate(action)
            }
        }
        recyclerView.adapter = agentAdapter

        progressBar.visibility = View.VISIBLE
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.getAgents("es-MX", true)
                allAgents = response.data ?: emptyList()
                agentAdapter.updateList(allAgents)
            } catch (e: Exception) {
                Log.e("AgentsFragment", "Error al cargar agentes", e)
            } finally {
                progressBar.visibility = View.GONE
            }
        }

        searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val filteredList = allAgents.filter {
                    it.displayName?.contains(s.toString(), ignoreCase = true) == true
                }
                agentAdapter.updateList(filteredList)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}