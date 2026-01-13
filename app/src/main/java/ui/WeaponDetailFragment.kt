package com.example.valorantapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.valorantapp.R // <-- ESTA LÍNEA ES LA QUE FALTA
import com.example.valorantapp.data.RetrofitClient
import kotlinx.coroutines.launch

class WeaponDetailFragment : Fragment() {

    private val args: WeaponDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weapon_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val weaponIcon = view.findViewById<ImageView>(R.id.ivWeaponDetailIcon)
        val weaponName = view.findViewById<TextView>(R.id.tvWeaponDetailName)
        val weaponStats = view.findViewById<TextView>(R.id.tvWeaponStats)

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.getWeapons("es-MX")
                val weapon = response.data?.find { it.uuid == args.weaponUuid }

                if (weapon != null) {
                    weaponName.text = weapon.displayName
                    Glide.with(this@WeaponDetailFragment).load(weapon.displayIcon).into(weaponIcon)

                    val stats = weapon.weaponStats
                    val statsText = """
                        Cadencia de Fuego: ${stats?.fireRate ?: "N/A"}
                        Tamaño del Cargador: ${stats?.magazineSize ?: "N/A"}
                        Tiempo de Equipamiento: ${stats?.equipTimeSeconds ?: "N/A"}s
                        Tiempo de Recarga: ${stats?.reloadTimeSeconds ?: "N/A"}s
                    """.trimIndent()
                    weaponStats.text = statsText
                }
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }
}