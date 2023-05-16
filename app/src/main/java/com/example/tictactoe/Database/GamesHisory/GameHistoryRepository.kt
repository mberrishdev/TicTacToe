package com.example.tictactoe.Database.GamesHisory

import com.example.tictactoe.Database.Users.User

class GameHistoryRepository(private val gameHistoryDao: GameHistoryDao) {
    fun insertGameHistory(gameHistory: GameHistory) : Long{
        return gameHistoryDao.insertGameHistory(gameHistory)
    }

    fun getGameHistoryByUser1(user1Id: Long) : List<GameHistory>{
        return gameHistoryDao.getGameHistoryByUser1(user1Id)
    }

    //fun getGameHistoryDaoWithUser1Id(user1Id: Long) : List<GameHistoryWithUser>{
      //  return gameHistoryDao.getGameHistoryDaoWithUser1Id(user1Id)
    //}
}