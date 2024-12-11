package com.example.seriousgame2024.classes

data class cPokemon(
    val id: Long,
    val nombres: Nombres,
    val tipos: List<Tipo>,
    val stats: Stats
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
    val hp: Long,
    val attack: Long,
    val defense: Long,
    val spAttack: Long,
    val spDefense: Long,
    val speed: Long
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
