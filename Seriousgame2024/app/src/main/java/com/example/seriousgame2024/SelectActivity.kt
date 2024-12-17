package com.example.seriousgame2024

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SelectActivity : AppCompatActivity() {
    lateinit var btnSafariBall: Button
    lateinit var btnPokeBall: Button
    lateinit var btnSuperBall: Button
    lateinit var btnUltraBall: Button
    lateinit var btnMasterBall: Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)
        btnSafariBall = findViewById<Button>(R.id.btnSafariBall)
        btnPokeBall = findViewById<Button>(R.id.btnPokeBall)
        btnSuperBall = findViewById<Button>(R.id.btnSuperBall)
        btnUltraBall = findViewById<Button>(R.id.btnUltraBall)
        btnMasterBall = findViewById<Button>(R.id.btnMasterBall)

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

    private fun goToTrivia() {
        val intent = Intent(this,TriviaGameActivity::class.java)
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