package com.example.valorantapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.valorantapp.R
import com.example.valorantapp.data.RetrofitClient
import kotlinx.coroutines.launch

class SkinDetailFragment : Fragment() {

    private val args: SkinDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_skin_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val skinImage = view.findViewById<ImageView>(R.id.ivSkinDetailImage)

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                // Para encontrar una skin, necesitamos recorrer todas las armas
                val weaponsResponse = RetrofitClient.instance.getWeapons("es-MX")
                var foundSkin: com.example.valorantapp.data.Skin? = null

                for (weapon in weaponsResponse.data ?: emptyList()) {
                    val skin = weapon.skins?.find { it.uuid == args.skinUuid }
                    if (skin != null) {
                        foundSkin = skin
                        break
                    }
                }

                if (foundSkin != null) {
                    Glide.with(this@SkinDetailFragment)
                        .load(foundSkin.displayIcon) // Usamos displayIcon, que suele ser la imagen completa
                        .into(skinImage)
                }

            } catch (e: Exception) {
                // Manejar error
            }
        }
    }
}