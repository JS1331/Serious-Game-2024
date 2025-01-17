package com.example.seriousgame2024

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.snackbar.Snackbar

class SelectActivity : AppCompatActivity() {
    lateinit var btnSafariBall: ImageButton
    lateinit var btnPokeBall: ImageButton
    lateinit var btnSuperBall: ImageButton
    lateinit var btnUltraBall: ImageButton
    lateinit var btnMasterBall: ImageButton
    var maxScore: Int = 0
    private val REQUEST_CODE_JOC_ACTIVITY = 1
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.menu)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Pokemon"
        supportActionBar?.setDisplayUseLogoEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        btnSafariBall = findViewById<ImageButton>(R.id.btnSafariBall)
        btnPokeBall = findViewById<ImageButton>(R.id.btnPokeBall)
        btnSuperBall = findViewById<ImageButton>(R.id.btnSuperBall)
        btnUltraBall = findViewById<ImageButton>(R.id.btnUltraBall)
        btnMasterBall = findViewById<ImageButton>(R.id.btnMasterBall)

        btnSafariBall.setOnClickListener(){
            goToTrivia()
        }

        btnPokeBall.setOnClickListener(){
            goToTrueOrFalse()
        }

        btnSuperBall.setOnClickListener(){
            goToWordle()
        }

        btnUltraBall.setOnClickListener(){
            goToStatBattle()
        }

        btnMasterBall.setOnClickListener(){
            goToGuessStats()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        // Configurar acción al hacer clic en el ícono
        val menuItem = menu?.findItem(R.id.icnLogo)
        menuItem?.icon?.setBounds(0, 0, 96, 96) // Establece el tamaño del ícono en píxeles
        menuItem?.setOnMenuItemClickListener {
            // Volver a MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Finaliza la actividad actual para evitar volver atrás
            true
        }

        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_JOC_ACTIVITY && resultCode == Activity.RESULT_OK) {
            data?.let {
                maxScore = it.getIntExtra("max_score", 0)
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "New max score received: $maxScore",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun goToTrivia() {
        val intent = Intent(this,TriviaGameActivity::class.java)
        intent.putExtra("max_score", maxScore)
        startActivity(intent)
    }

    private fun goToTrueOrFalse() {
        val intent = Intent(this,TrueFalseGameActivtiy::class.java)
        startActivity(intent)
    }

    private fun goToWordle(){
        val intent = Intent(this,WordleGameActivity::class.java)
        startActivity(intent)
    }

    private fun goToStatBattle(){
        val intent = Intent(this,StatBattleGameActivity::class.java)
        startActivity(intent)
    }

    private fun goToGuessStats(){
        val intent = Intent(this,GuessStatsActivity::class.java)
        startActivity(intent)
    }
}