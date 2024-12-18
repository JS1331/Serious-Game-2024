package com.example.seriousgame2024

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.seriousgame2024.classes.cPokemon
import com.google.gson.Gson

class Stats : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.stats)

        // Obtener el JSON del Intent
        val json = intent.getStringExtra("pokemon") ?: ""
        val pokemon = Gson().fromJson(json, cPokemon::class.java)

        // Mostrar la información del Pokémon
        findViewById<TextView>(R.id.tvName).text = pokemon.name.english
        findViewById<TextView>(R.id.tvID2).text = pokemon.id.toString()
        findViewById<TextView>(R.id.tvType1).text = pokemon.type.getOrNull(0) ?: "N/A"
        findViewById<TextView>(R.id.tvType2).text = pokemon.type.getOrNull(1) ?: "N/A"
        findViewById<TextView>(R.id.tvHp).text = pokemon.base.HP.toString()
        findViewById<TextView>(R.id.tvAtk).text = pokemon.base.Attack.toString()
        findViewById<TextView>(R.id.tvDef).text = pokemon.base.Defense.toString()
        findViewById<TextView>(R.id.tvSpA).text = pokemon.base.SpAttack.toString()
        findViewById<TextView>(R.id.tvSpD).text = pokemon.base.SpDefense.toString()
        findViewById<TextView>(R.id.tvSpe).text = pokemon.base.Speed.toString()

        // Cambiar colores del botón
        val btn = findViewById<Button>(R.id.btnBack)
        val type1 = pokemon.type.getOrNull(0) ?: "normal"
        val type2 = pokemon.type.getOrNull(1)

        btn.setBackgroundColor(getTypeColor(type1)) // Color de fondo basado en type1
        btn.setTextColor(type2?.let { getTypeColor(it) } ?: Color.BLACK) // Color de texto basado en type2 o negro por defecto

        // Configurar botón para regresar a PokedexActivity
        btn.setOnClickListener {
            finish() // Cierra la actividad actual y regresa a la anterior
        }
    }

    private fun getTypeColor(type: String): Int {
        return when (type.lowercase()) {
            "fire" -> getColor(R.color.fire)
            "water" -> getColor(R.color.water)
            "grass" -> getColor(R.color.grass)
            "electric" -> getColor(R.color.electric)
            "bug" -> getColor(R.color.bug)
            "rock" -> getColor(R.color.rock)
            "ground" -> getColor(R.color.ground)
            "dark" -> getColor(R.color.dark)
            "normal" -> getColor(R.color.normal)
            "ghost" -> getColor(R.color.ghost)
            "fighting" -> getColor(R.color.fighting)
            "steel" -> getColor(R.color.steel)
            "dragon" -> getColor(R.color.dragon)
            "fairy" -> getColor(R.color.fairy)
            "ice" -> getColor(R.color.ice)
            "flying" -> getColor(R.color.flying)
            "psychic" -> getColor(R.color.psychic)
            "poison" -> getColor(R.color.poison)
            else -> getColor(R.color.normal) // Color predeterminado
        }
    }
}

