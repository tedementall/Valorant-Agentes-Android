package com.example.valorantapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.valorantapp.R
import com.example.valorantapp.data.RetrofitClient
import kotlinx.coroutines.launch

class MapDetailFragment : Fragment() {

    private val args: MapDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapImage = view.findViewById<ImageView>(R.id.ivMapDetailImage)
        val mapName = view.findViewById<TextView>(R.id.tvMapDetailName)
        val mapStatus = view.findViewById<TextView>(R.id.tvMapRotationStatus)

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.getMaps("es-MX")
                val map = response.data?.find { it.uuid == args.mapUuid }

                if (map != null) {
                    mapName.text = map.displayName
                    Glide.with(this@MapDetailFragment).load(map.splash).into(mapImage)

                    if (map.coordinates == null) {
                        mapStatus.text = "Fuera de la rotación competitiva"
                        mapStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.valorant_cream))
                    } else {
                        mapStatus.text = "En la rotación competitiva actual"
                        mapStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.valorant_red))
                    }
                }
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }
}