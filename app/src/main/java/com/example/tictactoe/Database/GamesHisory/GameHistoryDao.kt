package com.example.tictactoe.Database.GamesHisory

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tictactoe.Database.Users.User

@Dao
interface GameHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGameHistory(gameHistory: GameHistory): Long

    @Query("SELECT * FROM  gamesHistory WHERE user1Id = :user1Id")
    fun getGameHistoryByUser1(user1Id: Long) : List<GameHistory>

    //@Query("SELECT * FROM gamesHistory INNER JOIN users ON gamesHistory.user1Id = users.id WHERE gamesHistory.user1Id = :userId")
    //fun getGameHistoryDaoWithUser1Id(userId: Long): List<GameHistoryWithUser>

}