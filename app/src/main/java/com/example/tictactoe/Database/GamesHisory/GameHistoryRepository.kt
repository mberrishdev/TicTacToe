package com.example.tictactoe.Database.GamesHisory

import com.example.tictactoe.Database.Users.User

class GameHistoryRepository(private val gameHistoryDao: GameHistoryDao) {
    fun insertGameHistory(gameHistory: GameHistory) : Long{
        return gameHistoryDao.insertGameHistory(gameHistory)
    }

    fun getGameHistoryByUser1(user1Id: Long) : List<GameHistory>{
        return gameHistoryDao.getGameHistoryByUser1(user1Id)
    }

    fun getGameHistoryById(id: Long) : GameHistory{
        return gameHistoryDao.getGameHistoryById(id)
    }

    fun updateGameHistory(gameHistory: GameHistory) {
        gameHistoryDao.updateGameHistory(gameHistory)
    }

    fun deleteGameHistories() {
        gameHistoryDao.deleteGameHistories()
    }
}