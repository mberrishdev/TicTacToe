package com.example.tictactoe

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoe.Database.AppDatabase
import com.example.tictactoe.Database.GamesHisory.GameHistoryRepository
import com.example.tictactoe.Database.Users.UserRepository

class SettingActivity : AppCompatActivity() {
    private lateinit var resetButton: Button
    private lateinit var userRepository: UserRepository
    private lateinit var gameHistoryRepository: GameHistoryRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        setRepositories()

        resetButton = findViewById(R.id.reset_button)
        resetButton.setOnClickListener {
            showResetConfirmationDialog()
        }
    }

    private fun showResetConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.reset_tictactoe_data)
        builder.setMessage(R.string.are_you_shore_to_reset)
        builder.setPositiveButton(R.string.yes) { _, _ ->
            resetTicTacToeData()
        }
        builder.setNegativeButton(R.string.no) { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun resetTicTacToeData() {
        gameHistoryRepository.deleteGameHistories()
        userRepository.deleteUsers()

        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
    }

    private fun setRepositories(){
        val userDao = AppDatabase.getInstance(applicationContext).userDao()
        userRepository = UserRepository(userDao)

        val gameHistoryDao = AppDatabase.getInstance(applicationContext).gameHistoryDao()
        gameHistoryRepository = GameHistoryRepository(gameHistoryDao)
    }
}