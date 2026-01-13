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
import androidx.recyclerview.widget.RecyclerView
import com.example.valorantapp.R
import com.example.valorantapp.data.RetrofitClient
import com.example.valorantapp.data.Spray
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class SpraysFragment : Fragment() {

    private lateinit var sprayAdapter: SprayAdapter
    private var allSprays: List<Spray> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sprays, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvSprays)
        val searchInput = view.findViewById<TextInputEditText>(R.id.etSprayName)

        sprayAdapter = SprayAdapter(emptyList())
        recyclerView.adapter = sprayAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.getSprays("es-MX")
                allSprays = response.data ?: emptyList()
                sprayAdapter.updateList(allSprays)
            } catch (e: Exception) {
                Log.e("SpraysFragment", "Error al cargar sprays", e)
            }
        }

        searchInput.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val filteredList = allSprays.filter {
                    it.displayName?.contains(s.toString(), ignoreCase = true) == true
                }
                sprayAdapter.updateList(filteredList)
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }
}