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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.valorantapp.R
import com.example.valorantapp.data.RetrofitClient
import com.example.valorantapp.data.Bundle as ValorantBundle
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class BundlesFragment : Fragment() {

    private lateinit var bundleAdapter: BundleAdapter
    private var allBundles: List<ValorantBundle> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bundles, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvBundles)
        val searchInput = view.findViewById<TextInputEditText>(R.id.etBundleName)

        bundleAdapter = BundleAdapter(emptyList()) { bundle ->
            bundle.uuid?.let { uuid ->
                val action = BundlesFragmentDirections.actionNavSkinsToBundleDetailFragment(uuid)
                findNavController().navigate(action)
            }
        }
        recyclerView.adapter = bundleAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.getBundles("es-MX")
                allBundles = response.data ?: emptyList()
                bundleAdapter.updateList(allBundles)
            } catch (e: Exception) {
                Log.e("BundlesFragment", "Error al cargar los bundles", e)
            }
        }

        searchInput.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val filteredList = allBundles.filter {
                    it.displayName?.contains(s.toString(), ignoreCase = true) == true
                }
                bundleAdapter.updateList(filteredList)
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }
}