package com.example.tictactoe.Database.Users

import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao) {
    val allUsers: LiveData<List<User>> = userDao.getAllUsers()

    fun getUsers(): List<User> {
        return userDao.getUsers()
    }

    fun getInternalUserName(): String {
        return userDao.getInternalUserName()
    }

    fun getInternalUserId(): Long {
        return userDao.getInternalUserId()
    }

    fun getUserIdByName(name: String): Long? {
        return userDao.getUserIdByName(name)
    }

    fun getInternalUser():User{
        return  userDao.getInternalUser()
    }

    fun getUserNameById(id: Long): String? {
        return userDao.getUserNameById(id)
    }

    fun insertUser(user: User) : Long{
       return userDao.insertUser(user)
    }

    fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }

    fun checkIfInternalUserExist(): Boolean {
        return userDao.getInternalUsers().size == 1
    }

    fun existByName(name: String): Boolean{
        return userDao.getUserByName(name).size == 1
    }

    fun deleteUsers(){
        userDao.deleteUsers()
    }

    fun updateUser(user: User) {
        userDao.updateUser(user)
    }
}
