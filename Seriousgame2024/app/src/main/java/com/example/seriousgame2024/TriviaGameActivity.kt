package com.example.seriousgame2024

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.seriousgame2024.classes.Pokemon
import com.example.seriousgame2024.classes.cPokemon
import com.example.seriousgame2024.classes.Pokemons
import com.google.gson.Gson
import kotlin.random.Random

class TriviaGameActivity : AppCompatActivity() {
    var pokemonPregunta: String = ""
    var tipo1: String = ""
    var tipo2: String = ""
    var tipo3: String = ""
    var tipo4: String = ""
    private lateinit var tvPokemon: TextView
    private lateinit var btnTipo1: Button
    private lateinit var btnTipo2: Button
    private lateinit var btnTipo3: Button
    private lateinit var btnTipo4: Button
    private lateinit var pokemonList: MutableList<cPokemon>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trivia_game)

        tvPokemon = findViewById(R.id.tvPokemon)
        btnTipo1 = findViewById(R.id.btnRed)
        btnTipo2 = findViewById(R.id.btnBlue)
        btnTipo3 = findViewById(R.id.btnGreen)
        btnTipo4 = findViewById(R.id.btnYellow)

        pokemonList = loadPokemonData()
        randomPokemon()
    }

    private fun loadPokemonData(): MutableList<Pokemon> {
        val json: String = this.assets.open("pokemon.json").bufferedReader().use { it.readText() }
        val gson = Gson()
        val pokemons: Pokemons = gson.fromJson(json, Pokemons::class.java)
        return pokemons.pokemons.toMutableList()
    }

    private fun randomPokemon() {
        if (pokemonList.isEmpty()) {
            tvPokemon.text = "No hay Pokémon disponibles"
            return
        }
        val randomIndex = Random.nextInt(pokemonList.size)
        pokemonPregunta = pokemonList[randomIndex].toString()
        tvPokemon.text = pokemonPregunta
    }
}
