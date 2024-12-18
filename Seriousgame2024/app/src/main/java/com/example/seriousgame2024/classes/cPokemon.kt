package com.example.seriousgame2024.classes


data class Poke(
    val pokemon: List<cPokemon>
)
data class cPokemon(
    val id: Int,
    val name: Nombres,       // Cambia "nombres" a "name" para coincidir con el JSON
    val type: List<String>,  // Cambia "tipos" a "type" para coincidir con el JSON
    val base: Stats          // Usa "base" para mapear directamente al JSON
)



// Clase para los nombres en diferentes idiomas
data class Nombres(
    val english: String,
    val japanese: String,
    val chinese: String,
    val french: String
)

// Clase para las estadísticas base
data class Stats(
    val HP: Int,
    val Attack: Int,
    val Defense: Int,
    val SpAttack: Int,
    val SpDefense: Int,
    val Speed: Int
)

// Enumeración para los tipos de Pokémon
enum class Tipo {
    Bug,
    Dark,
    Dragon,
    Electric,
    Fairy,
    Fighting,
    Fire,
    Flying,
    Ghost,
    Grass,
    Ground,
    Ice,
    Normal,
    Poison,
    Psychic,
    Rock,
    Steel,
    Water
}
