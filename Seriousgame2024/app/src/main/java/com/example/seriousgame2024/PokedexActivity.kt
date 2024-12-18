package com.example.seriousgame2024

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.seriousgame2024.adapter.PokemonAdapter
import com.example.seriousgame2024.classes.Poke
import com.example.seriousgame2024.classes.cPokemon
import com.google.gson.Gson

class PokedexActivity : AppCompatActivity() {
    private val originalList: MutableList<cPokemon> = ArrayList()
    private lateinit var pokemonAdapter: PokemonAdapter
    private lateinit var oakPokedex: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pokedex)

        // Configurar Toolbar
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.menu)
        setSupportActionBar(toolbar)

        supportActionBar?.title = "Pokemon"
        supportActionBar?.setDisplayUseLogoEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Configurar RecyclerView
        setupRecyclerView()

        // Cargar datos desde el JSON
        loadPokemonData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    private fun setupRecyclerView() {
        oakPokedex = findViewById(R.id.rvPokedex)
        oakPokedex.setHasFixedSize(true)
        oakPokedex.layoutManager = LinearLayoutManager(this)

        // Inicializar adaptador vacío y luego llenarlo con datos
        pokemonAdapter = PokemonAdapter(originalList, this)
        oakPokedex.adapter = pokemonAdapter
    }

    private fun loadPokemonData() {
        try {
            val json: String = assets.open("pokemon.json").bufferedReader().use { it.readText() }
            val pokeList: Poke = Gson().fromJson(json, Poke::class.java)

            originalList.clear()
            originalList.addAll(pokeList.pokemon)

            pokemonAdapter.notifyDataSetChanged()
            Log.d("PokedexActivity", "Pokémon cargados: ${pokeList.pokemon.size}")
        } catch (e: Exception) {
            Log.e("PokedexActivity", "Error al cargar los datos: ${e.message}")
        }
    }
}
