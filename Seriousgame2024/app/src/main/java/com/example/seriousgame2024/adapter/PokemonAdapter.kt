package com.example.seriousgame2024.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.seriousgame2024.R
import com.example.seriousgame2024.Stats
import com.example.seriousgame2024.classes.cPokemon
import com.google.gson.Gson

class PokemonAdapter(
    private var pokemonList: MutableList<cPokemon>, // Lista de Pokémon
    private val context: Context // Contexto de la actividad
) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        val tipos = pokemon.type

        // Configurar datos del Pokémon
        holder.tvNombre.text = pokemon.name.english
        holder.tvID.text = pokemon.id.toString()
        holder.tvTipo1.text = tipos.getOrNull(0) ?: "N/A"
        holder.tvTipo2.text = tipos.getOrNull(1) ?: "N/A"

        // Configurar botón Stats
        holder.btnStats.setOnClickListener {
            // Crear un Intent para abrir Stats y enviar el Pokémon seleccionado
            val intent = Intent(context, Stats::class.java).apply {
                putExtra("pokemon", Gson().toJson(pokemon))
            }
            context.startActivity(intent)
        }

        // Configurar color del CardView basado en el tipo principal
        val primaryType = tipos.getOrNull(0) ?: "Normal"
        holder.cardView.setCardBackgroundColor(getTypeColor(primaryType))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.pokedex_row, parent, false)
        return PokemonViewHolder(view)
    }

    override fun getItemCount(): Int = pokemonList.size

    private fun getTypeColor(type: String): Int {
        return when (type.lowercase()) {
            "fire" -> context.getColor(R.color.fire)
            "water" -> context.getColor(R.color.water)
            "grass" -> context.getColor(R.color.grass)
            "electric" -> context.getColor(R.color.electric)
            "bug" -> context.getColor(R.color.bug)
            "rock" -> context.getColor(R.color.rock)
            "ground" -> context.getColor(R.color.ground)
            "dark" -> context.getColor(R.color.dark)
            "normal" -> context.getColor(R.color.normal)
            "ghost" -> context.getColor(R.color.ghost)
            "fighting" -> context.getColor(R.color.fighting)
            "steel" -> context.getColor(R.color.steel)
            "dragon" -> context.getColor(R.color.dragon)
            "fairy" -> context.getColor(R.color.fairy)
            "ice" -> context.getColor(R.color.ice)
            "flying" -> context.getColor(R.color.flying)
            "psychic" -> context.getColor(R.color.psychic)
            "poison" -> context.getColor(R.color.poison)
            else -> context.getColor(R.color.normal) // Color predeterminado
        }
    }

    class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombre)
        val tvID: TextView = itemView.findViewById(R.id.tvID)
        val tvTipo1: TextView = itemView.findViewById(R.id.tvTipo1)
        val tvTipo2: TextView = itemView.findViewById(R.id.tvTipo2)
        val cardView: CardView = itemView.findViewById(R.id.card)
        val btnStats: AppCompatButton = itemView.findViewById(R.id.btnStats)
    }
}
