package com.example.tictactoe.Database.GamesHisory

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.tictactoe.Database.Users.User

@Dao
interface GameHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGameHistory(gameHistory: GameHistory): Long
}