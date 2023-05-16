package com.example.tictactoe.Database.GamesHisory

import com.example.tictactoe.Database.Users.User

class GameHistoryRepository(private val gameHistoryDao: GameHistoryDao) {
    suspend fun insertGameHistory(gameHistory: GameHistory) : Long{
        return gameHistoryDao.insertGameHistory(gameHistory)
    }
}