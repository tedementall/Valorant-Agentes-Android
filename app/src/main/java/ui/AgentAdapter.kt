package com.example.valorantapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.valorantapp.R
import com.example.valorantapp.data.Agent

class AgentAdapter(
    private var agentList: List<Agent>,
    private val onAgentClicked: (Agent) -> Unit
) : RecyclerView.Adapter<AgentAdapter.AgentViewHolder>() {

    class AgentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val agentIcon: ImageView = itemView.findViewById(R.id.ivAgentIconItem)
        val agentName: TextView = itemView.findViewById(R.id.tvAgentNameItem)
        val agentRole: TextView = itemView.findViewById(R.id.tvAgentRoleItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_agent, parent, false)
        return AgentViewHolder(view)
    }

    override fun onBindViewHolder(holder: AgentViewHolder, position: Int) {
        val agent = agentList[position]
        holder.agentName.text = agent.displayName
        holder.agentRole.text = agent.role?.displayName ?: ""

        Glide.with(holder.itemView.context)
            .load(agent.displayIcon)
            .into(holder.agentIcon)

        holder.itemView.setOnClickListener {
            onAgentClicked(agent)
        }
    }

    override fun getItemCount(): Int {
        return agentList.size
    }

    fun updateList(newList: List<Agent>) {
        agentList = newList
        notifyDataSetChanged()
    }
}