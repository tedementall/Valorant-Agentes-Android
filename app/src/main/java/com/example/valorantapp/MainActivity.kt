package com.example.valorantapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.valorantapp.data.*
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import java.lang.Exception
import com.example.valorantapp.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchInput = findViewById<TextInputEditText>(R.id.etAgentName)
        val searchButton = findViewById<Button>(R.id.btnSearch)
        val resultText = findViewById<TextView>(R.id.tvResult)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val agentIcon = findViewById<ImageView>(R.id.ivAgentIcon)

        searchButton.setOnClickListener {
            val agentName = searchInput.text.toString()

            if (agentName.isNotBlank()) {
                progressBar.visibility = View.VISIBLE
                resultText.text = ""
                agentIcon.setImageResource(0)

                lifecycleScope.launch {
                    try {

                        val response = RetrofitClient.instance.getAgents("es-MX", true)

                        if (response.data != null) {
                            val foundAgent = response.data.find {
                                it.displayName?.equals(agentName, ignoreCase = true) == true
                            }

                            if (foundAgent != null) {
                                resultText.text = foundAgent.description

                                Glide.with(this@MainActivity)
                                    .load(foundAgent.displayIcon)
                                    .into(agentIcon)

                            } else {
                                resultText.text = "Agente no encontrado."
                            }
                        } else {
                            resultText.text = "La API no devolvió datos de agentes."
                        }

                        progressBar.visibility = View.GONE

                    } catch (e: Exception) {
                        progressBar.visibility = View.GONE
                        resultText.text = "Error: ${e.message}"
                        Log.e("MainActivity", "Error en la API", e)
                    }
                }
            }
        }
    }
}