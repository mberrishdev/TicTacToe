package com.example.tictactoe.Database

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.tictactoe.Database.Users.User
import com.example.tictactoe.Database.Users.UserRepository

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {
    val allUsers: LiveData<List<User>> = userRepository.allUsers

    fun getUserByName(userName: String): LiveData<User> {
        return userRepository.getUserByName(userName)
    }

    suspend fun insertUser(user: User) {
        userRepository.insertUser(user)
    }

    suspend fun deleteUser(user: User) {
        userRepository.deleteUser(user)
    }
}
