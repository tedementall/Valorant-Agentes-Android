package com.example.valorantapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.valorantapp.R
import com.example.valorantapp.data.Bundle as ValorantBundle

class BundleAdapter(
    private var bundleList: List<ValorantBundle>,
    private val onBundleClicked: (ValorantBundle) -> Unit
) : RecyclerView.Adapter<BundleAdapter.BundleViewHolder>() {

    class BundleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bundleImage: ImageView = itemView.findViewById(R.id.ivBundleImage)
        val bundleName: TextView = itemView.findViewById(R.id.tvBundleName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BundleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_bundle, parent, false)
        return BundleViewHolder(view)
    }

    override fun onBindViewHolder(holder: BundleViewHolder, position: Int) {
        val bundle = bundleList[position]
        holder.bundleName.text = bundle.displayName
        Glide.with(holder.itemView.context).load(bundle.displayIcon).into(holder.bundleImage)
        holder.itemView.setOnClickListener { onBundleClicked(bundle) }
    }

    override fun getItemCount() = bundleList.size

    fun updateList(newList: List<ValorantBundle>) {
        bundleList = newList
        notifyDataSetChanged()
    }
}