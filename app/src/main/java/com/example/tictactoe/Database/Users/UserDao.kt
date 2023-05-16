package com.example.tictactoe.Database.Users

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAllUsers(): LiveData<List<User>>

    @Query("SELECT * FROM users WHERE user_name = :name")
    fun getUserByName(name: String): List<User>

    @Query("SELECT id FROM users WHERE user_name = :name")
    suspend fun getUserIdByName(name: String): Long?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM users WHERE is_external = 0")
    suspend fun getInternalUsers(): List<User>

    @Query("SELECT * FROM users")
    fun getUsers(): List<User>

    @Query("SELECT user_name FROM users WHERE is_external = 0")
    suspend fun getInternalUserName(): String
}
