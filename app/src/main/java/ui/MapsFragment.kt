package com.example.valorantapp.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.valorantapp.R
import com.example.valorantapp.data.MapData
import com.example.valorantapp.data.RetrofitClient
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class MapsFragment : Fragment() {

    private lateinit var mapAdapter: MapAdapter
    private var allMaps: List<MapData> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvMaps)
        val searchInput = view.findViewById<TextInputEditText>(R.id.etMapName)

        // Ajustamos el padding para la barra de navegación
        ViewCompat.setOnApplyWindowInsetsListener(recyclerView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(bottom = systemBars.bottom)
            insets
        }

        // Creamos el adaptador con la acción de navegar
        mapAdapter = MapAdapter(emptyList()) { map ->
            map.uuid?.let { uuid ->
                val action = MapsFragmentDirections.actionNavMapsToMapDetailFragment(uuid)
                findNavController().navigate(action)
            }
        }
        recyclerView.adapter = mapAdapter

        // Cargamos los mapas
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.getMaps("es-MX")
                allMaps = response.data ?: emptyList()
                mapAdapter.updateList(allMaps)
            } catch (e: Exception) {
                Log.e("MapsFragment", "Error al cargar mapas", e)
            }
        }

        // Lógica para el buscador
        searchInput.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val filteredList = allMaps.filter {
                    it.displayName?.contains(s.toString(), ignoreCase = true) == true
                }
                mapAdapter.updateList(filteredList)
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }
}