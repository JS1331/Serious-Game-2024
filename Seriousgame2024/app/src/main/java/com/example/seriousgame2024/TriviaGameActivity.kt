package com.example.seriousgame2024

import android.annotation.SuppressLint
import android.app.Activity
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

class TriviaGameActivity : AppCompatActivity() {

    private lateinit var tvPokemon: TextView
    private lateinit var tvScore: TextView
    private lateinit var tvMaxScore: TextView
    private lateinit var btnTipo1: Button
    private lateinit var btnTipo2: Button
    private lateinit var btnTipo3: Button
    private lateinit var btnTipo4: Button
    private lateinit var pokeList: List<cPokemon>

    var pokemonPregunta: String = ""
    var tipoPregunta: String = "w"
    var score: Int = 0
    var maxScore: Int = 0
    var btnClicked: Int = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trivia_game)

        // Inicializa las vistas
        tvPokemon = findViewById(R.id.tvPokemon)
        tvScore = findViewById(R.id.tvScore)
        tvMaxScore = findViewById(R.id.tvMaxScore)
        btnTipo1 = findViewById(R.id.btnRed)
        btnTipo2 = findViewById(R.id.btnBlue)
        btnTipo3 = findViewById(R.id.btnGreen)
        btnTipo4 = findViewById(R.id.btnYellow)

        // Recupera el puntaje máximo pasado
        maxScore = (intent.getSerializableExtra("max_score") as Int)

        tvScore.text = score.toString()
        tvMaxScore.text = maxScore.toString()

        pokeList = loadPokemonData()
        randomPokemon()

        // Asignar los listeners de los botones
        btnTipo1.setOnClickListener {
            btnClicked = 1
            checkAnswer(btnClicked)
        }

        btnTipo2.setOnClickListener {
            btnClicked = 2
            checkAnswer(btnClicked)
        }

        btnTipo3.setOnClickListener {
            btnClicked = 3
            checkAnswer(btnClicked)
        }

        btnTipo4.setOnClickListener {
            btnClicked = 4
            checkAnswer(btnClicked)
        }

        loadMaxScore() // Cargar el puntaje máximo desde la base de datos
    }

    // Cargar los datos de los Pokémon desde un archivo JSON
    private fun loadPokemonData(): List<cPokemon> {
        return try {
            // Leer el archivo JSON
            val json: String = assets.open("pokemon.json").bufferedReader().use { it.readText() }

            // Deserializar el JSON en un objeto de tipo Poke
            val pokeData = Gson().fromJson(json, Poke::class.java)

            // Extraer la lista de Pokémon desde el objeto Poke
            pokeData.pokemon
        } catch (e: Exception) {
            Log.e("TriviaGameActivity", "Error loading or parsing JSON", e)
            emptyList()
        }
    }

    // Cargar el puntaje máximo desde la base de datos
    private fun loadMaxScore() {
        lifecycleScope.launch {
            val currentMaxScore = withContext(Dispatchers.IO) {
                val db = AppDatabase.getDatabase(this@TriviaGameActivity)
                db.gameScoreDao().getMaxScore("Trivia Game") ?: 0
            }

            // Actualizar la UI con el puntaje máximo
            runOnUiThread {
                tvMaxScore.text = currentMaxScore.toString()
                maxScore = currentMaxScore
            }
        }
    }

    // Guardar el puntaje actual si es mayor al puntaje máximo
    private fun saveScoreToDatabase() {
        val db = AppDatabase.getDatabase(this)
        lifecycleScope.launch {
            try {
                val currentMaxScore = withContext(Dispatchers.IO) {
                    db.gameScoreDao().getMaxScore("Trivia Game") ?: 0
                }

                if (score > currentMaxScore) {
                    val gameScore = GameScore(
                        gameName = "Trivia Game",
                        score = score,
                        maxScore = score
                    )

                    withContext(Dispatchers.IO) {
                        db.gameScoreDao().insertGameScore(gameScore)
                    }

                    // Actualiza el puntaje máximo mostrado en la UI
                    withContext(Dispatchers.Main) {
                        tvMaxScore.text = score.toString()
                    }

                    Log.d("TriviaGameActivity", "New Max Score saved: $score")
                }
            } catch (e: Exception) {
                Log.e("TriviaGameActivity", "Error saving score to database", e)
            }
        }
    }

    // Generar un Pokémon aleatorio para mostrar en la pregunta
    private fun randomPokemon() {
        if (pokeList.isEmpty()) {
            tvPokemon.text = "No hay Pokémon disponibles"
            return
        }

        val randomIndex = Random.nextInt(pokeList.size)
        pokemonPregunta = pokeList[randomIndex].name.english
        tvPokemon.text = pokemonPregunta
        tipoPregunta = pokeList[randomIndex].type[0]
        correctAnswer()
    }

    // Asignar las respuestas correctas e incorrectas a los botones
    private fun correctAnswer() {
        val randomCorrect = Random.nextInt(1, 5)

        // Lista de tipos posibles de Pokémon
        val allTypes = listOf(
            "Bug", "Dark", "Dragon", "Electric", "Fairy", "Fighting", "Fire",
            "Flying", "Ghost", "Grass", "Ground", "Ice", "Normal", "Poison",
            "Psychic", "Rock", "Steel", "Water"
        )

        // Seleccionar tipos incorrectos al azar
        val incorrectTypes = allTypes.filter { it != tipoPregunta }.shuffled().take(3)

        // Asignar respuestas a los botones
        when (randomCorrect) {
            1 -> {
                btnTipo1.text = tipoPregunta
                btnTipo2.text = incorrectTypes[0]
                btnTipo3.text = incorrectTypes[1]
                btnTipo4.text = incorrectTypes[2]
            }
            2 -> {
                btnTipo2.text = tipoPregunta
                btnTipo1.text = incorrectTypes[0]
                btnTipo3.text = incorrectTypes[1]
                btnTipo4.text = incorrectTypes[2]
            }
            3 -> {
                btnTipo3.text = tipoPregunta
                btnTipo1.text = incorrectTypes[0]
                btnTipo2.text = incorrectTypes[1]
                btnTipo4.text = incorrectTypes[2]
            }
            4 -> {
                btnTipo4.text = tipoPregunta
                btnTipo1.text = incorrectTypes[0]
                btnTipo2.text = incorrectTypes[1]
                btnTipo3.text = incorrectTypes[2]
            }
        }
    }

    // Comprobar la respuesta seleccionada
    private fun checkAnswer(btnClicked: Int) {
        when (btnClicked) {
            1 -> {
                if (btnTipo1.text == tipoPregunta) {
                    score += 10
                    updateScore()
                    randomPokemon()
                } else {
                    showSnackbarAndFinish()
                }
            }
            2 -> {
                if (btnTipo2.text == tipoPregunta) {
                    score += 10
                    updateScore()
                    randomPokemon()
                } else {
                    showSnackbarAndFinish()
                }
            }
            3 -> {
                if (btnTipo3.text == tipoPregunta) {
                    score += 10
                    updateScore()
                    randomPokemon()
                } else {
                    showSnackbarAndFinish()
                }
            }
            4 -> {
                if (btnTipo4.text == tipoPregunta) {
                    score += 10
                    updateScore()
                    randomPokemon()
                } else {
                    showSnackbarAndFinish()
                }
            }
        }
    }

    // Mostrar un mensaje de error si la respuesta es incorrecta
    private fun showSnackbarAndFinish() {
        Snackbar.make(
            findViewById(android.R.id.content),
            "Your answer is incorrect. The correct answer was $tipoPregunta",
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

    // Actualizar el puntaje y verificar si es el máximo
    private fun updateScore() {
        tvScore.text = score.toString()

        if (score > maxScore) {
            maxScore = score
            tvMaxScore.text = maxScore.toString()
            saveScoreToDatabase() // Guardar el nuevo puntaje máximo en la base de datos
        }
    }
}
