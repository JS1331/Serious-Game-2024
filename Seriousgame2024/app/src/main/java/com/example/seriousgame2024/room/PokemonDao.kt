package com.example.seriousgame2024.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GameScoreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGameScore(gameScore: GameScore)

    @Query("SELECT MAX(maxScore) FROM game_score_table WHERE gameName = :gameName")
    fun getMaxScore(gameName: String): Int? // Devuelve Int opcional

}

