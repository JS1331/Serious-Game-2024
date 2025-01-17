package com.example.seriousgame2024

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import com.example.seriousgame2024.classes.Poke
import com.example.seriousgame2024.classes.cPokemon
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlin.random.Random

class TriviaGameActivity : AppCompatActivity() {
    var pokemonPregunta: String = ""
    var tipoPregunta: String = "w"
    var score: Int = 0
    var maxScore: Int = 0
    var btnClicked: Int = 0
    private lateinit var tvPokemon: TextView
    private lateinit var tvScore: TextView
    private lateinit var tvMaxScore: TextView
    private lateinit var btnTipo1: Button
    private lateinit var btnTipo2: Button
    private lateinit var btnTipo3: Button
    private lateinit var btnTipo4: Button
    private lateinit var pokeList: List<cPokemon>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trivia_game)
        maxScore = (intent.getSerializableExtra("max_score") as Int)
        //tvMaxScore.text = maxScore.toString()
        tvPokemon = findViewById(R.id.tvPokemon)
        tvScore = findViewById(R.id.tvScore)
        tvScore.text = score.toString()
        tvMaxScore = findViewById(R.id.tvMaxScore)
        tvMaxScore.text = maxScore.toString()
        btnTipo1 = findViewById(R.id.btnRed)
        btnTipo2 = findViewById(R.id.btnBlue)
        btnTipo3 = findViewById(R.id.btnGreen)
        btnTipo4 = findViewById(R.id.btnYellow)

        pokeList = loadPokemonData()
        randomPokemon()
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

    }

    private fun loadPokemonData(): List<cPokemon> {
        val json: String = assets.open("pokemon.json").bufferedReader().use { it.readText() }
        return try {
            // Cambia el tipo de deserialización a un arreglo de `cPokemon`
            Gson().fromJson(json, Array<cPokemon>::class.java).toList()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList() // Retorna una lista vacía en caso de error
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
        tipoPregunta = pokeList[randomIndex].type[0]
        correctAnswer()
    }

    private fun correctAnswer() {
        val randomCorrect = Random.nextInt(1, 5)

        // Lista de todos los tipos posibles
        val allTypes = listOf(
            "Bug", "Dark", "Dragon", "Electric", "Fairy", "Fighting", "Fire",
            "Flying", "Ghost", "Grass", "Ground", "Ice", "Normal", "Poison",
            "Psychic", "Rock", "Steel", "Water"
        )

        // Tipos aleatorios sin incluir el tipo correcto
        val incorrectTypes = allTypes.filter { it != tipoPregunta }.shuffled().take(3)

        // Asignar valores a los botones
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

    private fun checkAnswer(btnClicked: Int){
        when(btnClicked){
            1 ->{
                if(btnTipo1.text == tipoPregunta){
                    score+= 10
                    updateScore()
                    randomPokemon()
                }
                else{
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "Your answer is incorrect the true answer was " + tipoPregunta,
                        Snackbar.LENGTH_LONG
                    ).show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        val resultIntent = Intent().apply{
                            putExtra("max_score",maxScore)
                        }
                        setResult(Activity.RESULT_OK, resultIntent)
                        finish() // Esto se ejecutará después de 2 segundos
                    }, 2000)
                }
            }
            2 -> {
                if (btnTipo2.text == tipoPregunta) {
                    score += 10
                    updateScore()
                    randomPokemon()
                } else {
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
            }
            3 -> {
                if (btnTipo3.text == tipoPregunta) {
                    score += 10
                    updateScore()
                    randomPokemon()
                } else {
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
            }
            4 -> {
                if (btnTipo4.text == tipoPregunta) {
                    score += 10
                    updateScore()
                    randomPokemon()
                } else {
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
            }
        }
    }

    private fun updateScore(){
        tvScore.text = score.toString()
        if(score > maxScore){
            maxScore = score
            tvMaxScore.text = maxScore.toString()
        }
    }

}
