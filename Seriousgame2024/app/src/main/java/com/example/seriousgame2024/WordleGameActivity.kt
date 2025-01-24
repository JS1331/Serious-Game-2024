package com.example.seriousgame2024

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.seriousgame2024.classes.Poke
import com.example.seriousgame2024.classes.cPokemon
import com.google.gson.Gson

class WordleGameActivity : AppCompatActivity() {
    private lateinit var tvCounter: TextView
    private lateinit var pokeList: MutableList<cPokemon> // Declarar como MutableList

    var counter: Int = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wordle_game)

        tvCounter = findViewById(R.id.tvcontador)

        // Inicializar pokeList con los datos cargados del archivo JSON
        pokeList = loadPokemonData().toMutableList()

        contar() // Llamar a contar después de inicializar pokeList
    }

    private fun loadPokemonData(): List<cPokemon> {
        return try {
            // Leer el archivo JSON
            val json: String = assets.open("pokemon.json").bufferedReader().use { it.readText() }

            // Deserializar el JSON en un objeto de tipo Poke
            val pokeData = Gson().fromJson(json, Poke::class.java)

            // Extraer la lista de Pokémon desde el objeto Poke
            pokeData.pokemon
        } catch (e: Exception) {
            Log.e("WordleGameActivity", "Error loading or parsing JSON", e)
            emptyList() // Devolver una lista vacía si hay un error
        }
    }

    private fun contar() {
        // Filtrar los Pokémon que tienen exactamente 5 letras en su nombre en inglés
        val fiveLetterPokemonNames = pokeList.filter { it.name.english.length == 6 }

        // Mostrar los nombres de Pokémon de 5 letras en inglés en el TextView
        var names = ""
        var i = 1
        for (pokemon in fiveLetterPokemonNames) {
            names += pokemon.name.english + " " + i

        i++// Usamos pokemon.name.english para acceder al nombre en inglés
        }

        // Mostrar los nombres filtrados en el TextView
        tvCounter.text = names
    }
}
