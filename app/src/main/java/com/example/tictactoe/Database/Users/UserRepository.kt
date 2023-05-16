package com.example.tictactoe.Database.Users

import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao) {
    val allUsers: LiveData<List<User>> = userDao.getAllUsers()


    fun getUsers(): List<User> {
        return userDao.getUsers()
    }

    suspend fun getInternalUserName(): String {
        return userDao.getInternalUserName()
    }

    suspend fun getInternalUserId(): Long {
        return userDao.getInternalUserId()
    }

    fun getUserIdByName(name: String): Long? {
        return userDao.getUserIdByName(name)
    }

    fun getUserNameById(id: Long): String? {
        return userDao.getUserNameById(id)
    }

    fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }

    suspend fun checkIfInternalUserExist(): Boolean {
        return userDao.getInternalUsers().size == 1
    }

    suspend fun existByName(name: String): Boolean{
        return userDao.getUserByName(name).size == 1
    }
}
