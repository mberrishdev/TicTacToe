package com.example.tictactoe.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tictactoe.Database.GamesHisory.GameHistory
import com.example.tictactoe.Database.GamesHisory.GameHistoryDao
import com.example.tictactoe.Database.Users.User
import com.example.tictactoe.Database.Users.UserDao

@Database(entities = [User::class, GameHistory::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun gameHistoryDao(): GameHistoryDao

    companion object {
        private const val DATABASE_NAME = "app_database"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
