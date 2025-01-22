package com.example.seriousgame2024
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.seriousgame2024.classes.Poke
import com.example.seriousgame2024.classes.cPokemon
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlin.random.Random
class TrueFalseGameActivtiy : AppCompatActivity() {

    private lateinit var tvPokemon: TextView
    private lateinit var tvPokedexNum: TextView
    private lateinit var btnFalse: Button
    private lateinit var btnTrue: Button
    private lateinit var pokeList: MutableList<cPokemon> // Cambiar a MutableList
    private lateinit var tvScore: TextView
    private lateinit var tvMaxScore: TextView
    var pokemonPregunta: String = ""
    var pokedexPregunta: Int = 0
    var score: Int = 0
    var maxScore: Int = 0
    var btnClicked: Int = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_true_false_game_activtiy)

        // Inicializar las vistas
        tvPokemon = findViewById<TextView>(R.id.tvPokemonTF)
        tvPokedexNum = findViewById<TextView>(R.id.tvPokedexNumTF)
        btnFalse = findViewById<Button>(R.id.btnFalse)
        btnTrue = findViewById<Button>(R.id.btnTrue)
        tvScore = findViewById<TextView>(R.id.tvScoreTF)
        tvScore.text = score.toString()
        tvMaxScore = findViewById<TextView>(R.id.tvMaxScoreTF)
        tvMaxScore.text = maxScore.toString()

        // Cargar los datos de Pokémon
        pokeList = loadPokemonData().toMutableList() // Asegúrate de que pokeList se inicialice correctamente

        // Ejecutar randomPokemon() después de cargar los datos
        randomPokemon()

        // Configurar los listeners de los botones
        btnTrue.setOnClickListener() {
            btnClicked = 1
            checkAnswer(btnClicked)
        }

        btnFalse.setOnClickListener() {
            btnClicked = 2
            checkAnswer(btnClicked)
        }
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
            Log.e("TrueFalseGameActivtiy", "Error loading or parsing JSON", e)
            emptyList()
        }
    }

    private fun randomPokemon() {
        if (pokeList.isEmpty()) {
            tvPokemon.text = "No hay Pokémon disponibles"
            return
        }
        val randomIndex = Random.nextInt(pokeList.size)
        pokemonPregunta = pokeList[randomIndex].name.english
        tvPokemon.text = pokemonPregunta
        pokedexPregunta = pokeList[randomIndex].id
        coinFlip()
    }

    private fun coinFlip() {
        val coin: Int = Random.nextInt(1, 3)
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
        when (btnClicked) {
            1 -> {
                if (tvPokedexNum.text == pokedexPregunta.toString()) {
                    score += 10
                    updateScore()
                    randomPokemon()
                } else {
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "Your answer is incorrect. The correct answer was FALSE",
                        Snackbar.LENGTH_LONG
                    ).show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        val resultIntent = Intent().apply {
                            putExtra("max_score", maxScore)
                        }
                        setResult(Activity.RESULT_OK, resultIntent)
                        finish()
                    }, 2000)
                }
            }
            2 -> {
                if (tvPokedexNum.text != pokedexPregunta.toString()) {
                    score += 10
                    updateScore()
                    randomPokemon()
                } else {
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "Your answer is incorrect. The correct answer was TRUE",
                        Snackbar.LENGTH_LONG
                    ).show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        val resultIntent = Intent().apply {
                            putExtra("max_score", maxScore)
                        }
                        setResult(Activity.RESULT_OK, resultIntent)
                        finish()
                    }, 2000)
                }
            }
        }
    }

    private fun updateScore() {
        tvScore.text = score.toString()
        if (score > maxScore) {
            maxScore = score
            tvMaxScore.text = maxScore.toString()
        }
    }
}
