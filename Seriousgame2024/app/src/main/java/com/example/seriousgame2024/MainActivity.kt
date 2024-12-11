package com.example.seriousgame2024

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    lateinit var btnJugar: Button
    lateinit var btnPokedex: Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnJugar = findViewById<Button>(R.id.btnJugar)
        btnPokedex = findViewById<Button>(R.id.btnPokedex)

        btnPokedex.setOnClickListener(){
            openPokedex()
        }
        btnJugar.setOnClickListener(){
            selectLevel()
        }
    }

    fun selectLevel(){
        val intent = Intent(this,SelectActivity::class.java)
        startActivity(intent)
    }
    fun openPokedex(){
        val intent = Intent(this,PokedexActivity::class.java)
        startActivity(intent)
    }
}