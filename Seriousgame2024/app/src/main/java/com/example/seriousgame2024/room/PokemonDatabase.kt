package com.example.seriousgame2024.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_score_table")
data class GameScore(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "gameName") val gameName: String,
    @ColumnInfo(name = "score") val score: Int,
    @ColumnInfo(name = "maxScore") val maxScore: Int
)



