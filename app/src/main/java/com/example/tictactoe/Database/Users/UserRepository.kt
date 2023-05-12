package com.example.tictactoe.Database.Users

import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao) {
    val allUsers: LiveData<List<User>> = userDao.getAllUsers()

    fun getUserByName(userName: String): LiveData<User> {
        return userDao.getUserByName(userName)
    }
    fun getUsers(): List<User> {
        return userDao.getUsers()
    }

    suspend fun getInternalUserName(): String {
        return userDao.getInternalUserName()
    }


    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }

    suspend fun checkIfInternalUserExist(): Boolean {
        return userDao.getInternalUsers().size == 1
    }
}
