package com.example.tictactoe.Database.GamesHisory

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.tictactoe.Database.Users.User
import java.util.Date

@Entity(
    tableName = "gamesHistory",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user1Id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user2Id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class GameHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "user1Id")
    val user1Id: Long,
    @ColumnInfo(name = "user2Id")
    val user2Id: Long,
    val user1Score: Int,
    val user2Score: Int
)
