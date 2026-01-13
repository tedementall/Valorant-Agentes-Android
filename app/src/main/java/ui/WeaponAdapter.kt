package com.example.valorantapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.valorantapp.R
import com.example.valorantapp.data.Weapon

class WeaponAdapter(
    private var weaponList: List<Weapon>,
    private val onWeaponClicked: (Weapon) -> Unit
) : RecyclerView.Adapter<WeaponAdapter.WeaponViewHolder>() {

    class WeaponViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val weaponIcon: ImageView = itemView.findViewById(R.id.ivWeaponIconItem)
        val weaponName: TextView = itemView.findViewById(R.id.tvWeaponNameItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeaponViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_weapon, parent, false)
        return WeaponViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeaponViewHolder, position: Int) {
        val weapon = weaponList[position]
        holder.weaponName.text = weapon.displayName

        Glide.with(holder.itemView.context)
            .load(weapon.displayIcon)
            .into(holder.weaponIcon)

        holder.itemView.setOnClickListener {
            onWeaponClicked(weapon)
        }
    }

    override fun getItemCount(): Int {
        return weaponList.size
    }

    fun updateList(newList: List<Weapon>) {
        weaponList = newList
        notifyDataSetChanged()
    }
}