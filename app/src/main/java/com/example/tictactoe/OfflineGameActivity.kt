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

    private lateinit var gameHistoryListFragment: GameHistoryListFragment

    lateinit var userRepository: UserRepository
        private set

    lateinit var gameHistoryRepository: GameHistoryRepository
        private set

    private var player1Name: String = "";
    private var player1Id: Long = 0;
    private var player2Name: String = "";
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

        lifecycleScope.launch() {
            player1Id =  userRepository.getInternalUserId()
        }

        openGameHistoryListFragment();
        //showPlayer2NamePopup();
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
               // savePayer2Name(name)
            }

            player2Name = name;
        }
    }

    private fun loadBoard(name: String):Boolean
    {
        return  true;
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

    private fun openGameHistoryListFragment(){
        gameHistoryListFragment = GameHistoryListFragment()
        val bundle = Bundle()
        bundle.putString("Player1Name", player1Name)
        bundle.putLong("Player1Id", player1Id)
        gameHistoryListFragment.arguments = bundle

        supportFragmentManager.beginTransaction()//.add(R.id.fragmentContainer, gameHistoryListFragment)
            .replace(R.id.fragmentContainer, gameHistoryListFragment)
            .commit()
    }

    fun handleItemClick(itemId: Long) {
        // Handle the item click event in the activity, for example:
        Toast.makeText(this, "Selected item ID: $itemId", Toast.LENGTH_SHORT).show()

        supportFragmentManager.beginTransaction()
            .remove(gameHistoryListFragment)
            .commit()
        supportFragmentManager.popBackStack()
    }
}