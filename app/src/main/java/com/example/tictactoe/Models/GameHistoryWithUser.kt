package com.example.tictactoe.Models

data class GameHistoryWithUser(
    val id : Long,
    val user1Score: Int,
    val user2Score: Int,
    val user2Name: String
) {
}
