package com.example.seriousgame2024

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.seriousgame2024.classes.Poke
import com.example.seriousgame2024.classes.cPokemon
import com.example.seriousgame2024.room.AppDatabase
import com.example.seriousgame2024.room.GameScore
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class TrueFalseGameActivtiy : AppCompatActivity() {

    private lateinit var tvPokemon: TextView
    private lateinit var tvPokedexNum: TextView
    private lateinit var btnFalse: Button
    private lateinit var btnTrue: Button
    private lateinit var tvScore: TextView
    private lateinit var tvMaxScore: TextView

    private lateinit var pokeList: List<cPokemon>
    private var pokemonPregunta: String = ""
    private var pokedexPregunta: Int = 0
    private var score: Int = 0
    private var maxScore: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_true_false_game_activtiy)

        tvPokemon = findViewById(R.id.tvPokemonTF)
        tvPokedexNum = findViewById(R.id.tvPokedexNumTF)
        btnFalse = findViewById(R.id.btnFalse)
        btnTrue = findViewById(R.id.btnTrue)
        tvScore = findViewById(R.id.tvScoreTF)
        tvMaxScore = findViewById(R.id.tvMaxScoreTF)

        // Inicializar puntajes en UI
        tvScore.text = score.toString()
        tvMaxScore.text = maxScore.toString()

        // Cargar datos de Pokémon desde JSON
        pokeList = loadPokemonData()

        if (pokeList.isEmpty()) {
            tvPokemon.text = "No hay Pokémon disponibles"
            return
        }

        // Cargar el maxScore desde la base de datos
        loadMaxScore()

        // Generar una pregunta aleatoria
        randomPokemon()

        btnTrue.setOnClickListener { checkAnswer(1) }
        btnFalse.setOnClickListener { checkAnswer(2) }
    }

    private fun loadPokemonData(): List<cPokemon> {
        return try {
            val json: String = assets.open("pokemon.json").bufferedReader().use { it.readText() }
            val pokeData = Gson().fromJson(json, Poke::class.java)
            pokeData.pokemon
        } catch (e: Exception) {
            Log.e("TrueFalseGame", "Error al cargar o analizar JSON", e)
            emptyList()
        }
    }

    private fun randomPokemon() {
        val randomIndex = Random.nextInt(pokeList.size)
        pokemonPregunta = pokeList[randomIndex].name.english
        pokedexPregunta = pokeList[randomIndex].id

        tvPokemon.text = pokemonPregunta
        coinFlip()
    }

    private fun coinFlip() {
        val coin = Random.nextInt(1, 3)
        when (coin) {
            1 -> tvPokedexNum.text = pokedexPregunta.toString()
            2 -> {
                var randomNum: Int
                do {
                    randomNum = Random.nextInt(1, 808)
                } while (randomNum == pokedexPregunta)
                tvPokedexNum.text = randomNum.toString()
            }
        }
    }

    private fun checkAnswer(btnClicked: Int) {
        val isCorrect = (btnClicked == 1 && tvPokedexNum.text.toString() == pokedexPregunta.toString()) ||
                (btnClicked == 2 && tvPokedexNum.text.toString() != pokedexPregunta.toString())

        if (isCorrect) {
            score += 10
            updateScore()
            randomPokemon()
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Respuesta incorrecta", Snackbar.LENGTH_LONG).show()
            delayAndNavigateBack()
        }
    }

    private fun delayAndNavigateBack() {
        // Esperar 2000 milisegundos y luego regresar a la actividad de selección
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, SelectActivity::class.java)
            startActivity(intent)
            finish() // Cierra la actividad actual para que no quede en el back stack
        }, 2000) // Retraso de 2000 ms
    }


    private fun updateScore() {
        tvScore.text = score.toString()
        if (score > maxScore) {
            maxScore = score
            tvMaxScore.text = maxScore.toString()
            saveMaxScore() // Guardar el nuevo maxScore en la base de datos
        }
    }

    private fun loadMaxScore() {
        val db = AppDatabase.getDatabase(this)
        lifecycleScope.launch(Dispatchers.IO) {
            val savedMaxScore = db.gameScoreDao().getMaxScore("TrueFalseGame") ?: 0
            withContext(Dispatchers.Main) {
                maxScore = savedMaxScore
                tvMaxScore.text = maxScore.toString()
            }
        }
    }

    private fun saveMaxScore() {
        val db = AppDatabase.getDatabase(this)
        lifecycleScope.launch(Dispatchers.IO) {
            db.gameScoreDao().insertGameScore(
                GameScore(
                    gameName = "TrueFalseGame",
                    score = score,
                    maxScore = maxScore
                )
            )
        }
    }
}
