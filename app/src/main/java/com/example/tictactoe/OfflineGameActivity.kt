package com.example.tictactoe

import BoardFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import android.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.example.tictactoe.Database.AppDatabase
import com.example.tictactoe.Database.GamesHisory.GameHistory
import com.example.tictactoe.Database.GamesHisory.GameHistoryRepository
import com.example.tictactoe.Database.Users.User
import com.example.tictactoe.Database.Users.UserRepository
import kotlinx.coroutines.launch

class OfflineGameActivity : AppCompatActivity(), BoardFragment.ButtonClickListener {
    lateinit var userRepository: UserRepository
        private set

    lateinit var gameHistoryRepository: GameHistoryRepository
        private set

    private var player1Name: String = "";
    private var player2Name: String = "";
    private var gameHistoryId: Long = 0;
    private var player1Score: Int = 0;
    private var player2Score: Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offline_game)

        val userDao = AppDatabase.getInstance(applicationContext).userDao()
        userRepository = UserRepository(userDao)

        val gameHistoryDao = AppDatabase.getInstance(applicationContext).gameHistoryDao()
        gameHistoryRepository = GameHistoryRepository(gameHistoryDao)

        lifecycleScope.launch() {
            player1Name =  userRepository.getInternalUserName()
        }

        showPlayer2NamePopup();
    }

    override fun onButtonClick(row: Int, col: Int,) {
        Toast.makeText(this, "asdadadadadad", Toast.LENGTH_SHORT).show()
        // Execute the desired method in the Activity class
        // when the button is clicked in the BoardFragment
    }

    private fun showPlayer2NamePopup() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.player2_name_popup, null)
        builder.setView(dialogView)
        builder.setTitle("Enter Player2's Name")
        builder.setCancelable(false)

        builder.setPositiveButton("Save and start playing.") { dialog, which ->
            val editTextName = dialogView.findViewById<EditText>(R.id.editTextName)
            val playerName = editTextName.text.toString()

            checkPlayer2Existence(playerName);

            createAndStartGame();
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun checkPlayer2Existence(name: String)
    {
        lifecycleScope.launch {
            val player2Exists =  userRepository.existByName(name)

            if (player2Exists) {
                loadBoard(name);
            } else {
                savePayer2Name(name)
            }

            player2Name = name;
        }
    }

    private fun loadBoard(name: String):Boolean
    {
        //todo
        if(true)
        {
            //get board between this 2 player
            //player1Score =
            //player2Score =
        }
        {
            createGameHistory()
        }
        return  true;
    }

    private fun savePayer2Name(name: String)
    {
        val newUser = User(
            userName = name,
            isExternal = true
        )
        lifecycleScope.launch() {
            userRepository.insertUser(newUser);
        }
    }

    private fun createAndStartGame()
    {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val boardFragment = BoardFragment.getInstance()
        fragmentTransaction.add(R.id.fragmentContainer, boardFragment)
        fragmentTransaction.commit()
        boardFragment.setButtonClickListener(this)
    }

    private fun createGameHistory(){
        lifecycleScope.launch() {
            val player1Id = userRepository.getUserIdByName(player1Name);
            val player2Id = userRepository.getUserIdByName(player2Name);

            if(player1Id !=null && player2Id !=null) {
                val gameHistory = GameHistory(
                    user1Id = player1Id ?: 0,
                    user2Id = player2Id ?: 0,
                    user1Score = 0,
                    user2Score = 0,
                )

                // lifecycleScope.launch() {
                gameHistoryId = gameHistoryRepository.insertGameHistory(gameHistory);
                // }
            }


        }
    }
}