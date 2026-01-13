package com.example.valorantapp.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.valorantapp.R
import com.example.valorantapp.data.RetrofitClient
import com.example.valorantapp.data.Weapon
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class WeaponsFragment : Fragment() {

    private lateinit var weaponAdapter: WeaponAdapter
    private var allWeapons: List<Weapon> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weapons, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvWeapons)
        val searchInput = view.findViewById<TextInputEditText>(R.id.etWeaponName)

        weaponAdapter = WeaponAdapter(emptyList()) { weapon ->
            weapon.uuid?.let { uuid ->
                val action = WeaponsFragmentDirections.actionNavWeaponsToWeaponDetailFragment(uuid)
                findNavController().navigate(action)
            }
        }
        recyclerView.adapter = weaponAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.getWeapons("es-MX")
                allWeapons = response.data ?: emptyList()
                weaponAdapter.updateList(allWeapons)
            } catch (e: Exception) {
                // Manejar error
            }
        }

        searchInput.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val filteredList = allWeapons.filter {
                    it.displayName?.contains(s.toString(), ignoreCase = true) == true
                }
                weaponAdapter.updateList(filteredList)
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }
}