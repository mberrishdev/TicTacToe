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
    fun getUserIdByName(name: String): Long?

    @Query("SELECT user_name FROM users WHERE id = :id")
    fun getUserNameById(id: Long): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("SELECT * FROM users WHERE is_external = 0")
    fun getInternalUsers(): List<User>

    @Query("SELECT * FROM users")
    fun getUsers(): List<User>

    @Query("SELECT user_name FROM users WHERE is_external = 0")
    fun getInternalUserName(): String

    @Query("SELECT id FROM users WHERE is_external = 0")
    fun getInternalUserId(): Long

    @Query("DELETE FROM users")
    fun deleteUsers()
}
