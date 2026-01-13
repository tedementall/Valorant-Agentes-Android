package com.example.valorantapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.valorantapp.R
import com.example.valorantapp.data.RetrofitClient
import com.example.valorantapp.data.Skin
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class BundleDetailFragment : Fragment() {

    private val args: BundleDetailFragmentArgs by navArgs()
    private lateinit var skinAdapter: SkinAdapter
    private val TAG = "VALORANT_DEBUG"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bundle_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvSkins)

        skinAdapter = SkinAdapter(emptyList()) { skin ->
            skin.uuid?.let { uuid ->
                val action = BundleDetailFragmentDirections.actionBundleDetailFragmentToSkinDetailFragment(uuid)
                findNavController().navigate(action)
            }
        }
        recyclerView.adapter = skinAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                Log.d(TAG, "Buscando skins para el pack con UUID: ${args.bundleUuid}")

                val bundlesDeferred = async { RetrofitClient.instance.getBundles("en-US") }
                val themesDeferred = async { RetrofitClient.instance.getThemes("en-US") }
                val weaponsDeferred = async { RetrofitClient.instance.getWeapons("en-US") }

                val bundlesResponse = bundlesDeferred.await()
                val themesResponse = themesDeferred.await()
                val weaponsResponse = weaponsDeferred.await()

                val selectedBundle = bundlesResponse.data?.find { it.uuid == args.bundleUuid }

                if (selectedBundle?.displayName != null) {
                    Log.d(TAG, "Pack encontrado: '${selectedBundle.displayName}'.")

                    val relatedTheme = themesResponse.data?.find { it.displayName == selectedBundle.displayName }

                    if (relatedTheme?.uuid != null) {
                        Log.d(TAG, "Tema correspondiente encontrado: '${relatedTheme.displayName}' con UUID: ${relatedTheme.uuid}")

                        val allSkins = weaponsResponse.data?.flatMap { it.skins ?: emptyList() }
                        val skinsOfBundle = allSkins?.filter { it.themeUuid == relatedTheme.uuid }

                        Log.d(TAG, "Total de skins encontradas para este tema: ${skinsOfBundle?.size ?: 0}")
                        skinAdapter.updateList(skinsOfBundle ?: emptyList())
                    } else {
                        Log.d(TAG, "No se encontró un tema que coincida con el nombre del pack. Probando por assetPath...")
                        // Plan B: Usar el método de assetPath
                        val regex = "StorefrontItem_(.*?)ThemeBundle".toRegex()
                        val match = regex.find(selectedBundle.assetPath ?: "")
                        val themeKeyword = match?.groups?.get(1)?.value

                        if (themeKeyword != null) {
                            Log.d(TAG, "Palabra clave del tema detectada: $themeKeyword")
                            val themeByAssetPath = themesResponse.data?.find { it.assetPath?.contains(themeKeyword, ignoreCase = true) == true }
                            if(themeByAssetPath != null) {
                                val allSkins = weaponsResponse.data?.flatMap { it.skins ?: emptyList() }
                                val skinsOfBundle = allSkins?.filter { it.themeUuid == themeByAssetPath.uuid }
                                Log.d(TAG, "Skins encontradas por assetPath: ${skinsOfBundle?.size ?: 0}")
                                skinAdapter.updateList(skinsOfBundle ?: emptyList())
                            } else {
                                Log.d(TAG, "No se encontró tema por assetPath tampoco.")
                            }
                        }
                    }
                } else {
                    Log.d(TAG, "No se encontró el pack en la lista de la API.")
                }

            } catch (e: Exception) {
                Log.e(TAG, "Error al cargar los detalles del bundle", e)
            }
        }
    }
}