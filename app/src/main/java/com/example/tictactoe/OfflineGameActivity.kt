package com.example.tictactoe

import BoardFragment
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.tictactoe.Database.AppDatabase
import com.example.tictactoe.Database.GamesHisory.GameHistory
import com.example.tictactoe.Database.GamesHisory.GameHistoryRepository
import com.example.tictactoe.Database.Users.User
import com.example.tictactoe.Database.Users.UserRepository

class OfflineGameActivity : AppCompatActivity(), BoardFragment.BoardFragmentListener {

    private lateinit var gameHistoryListFragment: GameHistoryListFragment
    private lateinit var userRepository: UserRepository
    private lateinit var gameHistoryRepository: GameHistoryRepository
    private var gameHistoryId: Long = 0
    private var player1Id: Long = 0
    private var player2Id: Long = 0
    private var player1Name: String = ""
    private var player2Name: String = ""
    private var player1Symbol: String = "X"
    private var player2Symbol: String = "O"
    private var player1Score: Int = 0
    private var player2Score: Int = 0

    private var gameBordFragment: BoardFragment =  BoardFragment.getInstance()  
    private lateinit var player1ScoreTextView: TextView
    private lateinit var player2ScoreTextView: TextView
    private lateinit var resetGameHistoryButton: Button
    private lateinit var resetGameButton: Button
    private lateinit var winnerPopupButton: Button
    private lateinit var turnTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offline_game)

        setRepositories()
        loadInternalUserData()
        setViews()

        openGameHistoryListFragment()

        setUserNameUpdater()
    }

    private fun openBoardFragment()
    {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        gameBordFragment = BoardFragment.getInstance()
        fragmentTransaction.add(R.id.gameBordFragment, gameBordFragment)
        fragmentTransaction.commit()
        gameBordFragment.setBoardFragmentListener(this)
    }

    override fun onButtonClick(row: Int, col: Int,) {
        updateDynamicViewValues()
    }

    override fun onWin(playerSymbol: String) {
        if(playerSymbol == player1Symbol)
        {
            player1Score++
        }else
        {
            player2Score ++
        }

        val gameHistory = GameHistory(
            id = gameHistoryId,
            user1Id = player1Id,
            user2Id = player2Id,
            user1Score = player1Score,
            user2Score = player2Score
        )

        gameHistoryRepository.updateGameHistory(gameHistory)

        updateDynamicViewValues()
        turnTextView.text = ""
    }

    private fun getCurrentFragment(): Fragment? {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentList = fragmentManager.fragments
        for (fragment in fragmentList) {
            if (fragment.isVisible) {
                return fragment
            }
        }
        return null
    }

    fun handleItemClick(gameHistoryId: Long) {
        supportFragmentManager.beginTransaction()
            .remove(gameHistoryListFragment)
            .commit()
        supportFragmentManager.popBackStack()

        loadBoardAndPlayer2Data(gameHistoryId)
        updateDynamicViewValues()
        updateViewsVisibility(isGameHistoryListFragmentOpen())
        openBoardFragment()
    }

    private fun loadBoardAndPlayer2Data(id: Long)
    {
        val gameHistory =  gameHistoryRepository.getGameHistoryById(id)
        gameHistoryId = gameHistory.id
        player2Id = gameHistory.user2Id
        player2Name = userRepository.getUserNameById(gameHistory.user2Id)?:""
        player1Score = gameHistory.user1Score
        player2Score = gameHistory.user2Score
    }

    private fun loadInternalUserData(){
        player1Name =  userRepository.getInternalUserName()
        player1Id =  userRepository.getInternalUserId()
    }

    private fun setViews(){
        player1ScoreTextView = findViewById(R.id.player1scoreTextView)
        player2ScoreTextView = findViewById(R.id.player2ScoreTextView)
        resetGameHistoryButton = findViewById(R.id.resetGameHistoryButton)
        resetGameButton = findViewById(R.id.resetGameButton)
        winnerPopupButton = findViewById(R.id.winnerPopupButton)
        turnTextView = findViewById(R.id.turnTextView)

        resetGameHistoryButton.setOnClickListener()
        {
            resetGameHistory()
        }

        resetGameButton.setOnClickListener()
        {
            resetGame()
        }

        winnerPopupButton.setOnClickListener()
        {
            showWinnerPopup()
        }
    }

    private fun resetGameHistory(){
        val gameHistory = GameHistory(
            id = gameHistoryId,
            user1Id = player1Id,
            user2Id = player2Id,
            user1Score = 0,
            user2Score = 0
        )

        gameHistoryRepository.updateGameHistory(gameHistory)
        player1Score = 0
        player2Score = 0
        updateDynamicViewValues()
    }

    private fun resetGame(){
        gameBordFragment.resetGame()
        updateDynamicViewValues()
    }

    private fun setRepositories(){
        val userDao = AppDatabase.getInstance(applicationContext).userDao()
        userRepository = UserRepository(userDao)

        val gameHistoryDao = AppDatabase.getInstance(applicationContext).gameHistoryDao()
        gameHistoryRepository = GameHistoryRepository(gameHistoryDao)
    }

    private fun openGameHistoryListFragment(){
        gameHistoryListFragment = GameHistoryListFragment()
        val bundle = Bundle()
        bundle.putString("Player1Name", player1Name)
        bundle.putLong("Player1Id", player1Id)
        gameHistoryListFragment.arguments = bundle

        supportFragmentManager.beginTransaction()//.add(R.id.fragmentContainer, gameHistoryListFragment)
            .replace(R.id.gameHistoryListFragment, gameHistoryListFragment)
            .commit()

        updateViewsVisibility(isGameHistoryListFragmentOpen())
    }

    private fun updateViewsVisibility(showViews: Boolean) {
        val visibility = if (showViews) View.VISIBLE else View.GONE
        player1ScoreTextView.visibility = visibility
        player2ScoreTextView.visibility = visibility
        resetGameHistoryButton.visibility = visibility
        resetGameButton.visibility = visibility
        winnerPopupButton.visibility = visibility
        turnTextView.visibility = visibility
        updateDynamicViewValues()
    }

    private fun isGameHistoryListFragmentOpen(): Boolean {
        val currentFragment = getCurrentFragment()
        return currentFragment != null && currentFragment.javaClass == GameHistoryListFragment::class.java
    }

    private fun updateDynamicViewValues(){
        player1ScoreTextView.text = getString(R.string.player1Score, player1Score.toString())
        player2ScoreTextView.text = getString(R.string.player2ScoreWithName, player2Name, player2Score.toString())

        val currentPlayerName = if(gameBordFragment.currentPlayer == BoardFragment.Player.X) player1Name else player2Name
        turnTextView.text =  getString(R.string.player_turn, gameBordFragment.currentPlayer.symbol, currentPlayerName)
    }

    private fun showWinnerPopup()
    {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(R.string.are_you_sure)
        builder.setPositiveButton(R.string.yes) { _, _ ->
            showWinnerNameAndGiff()
        }
        builder.setNegativeButton(R.string.no) { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun showWinnerNameAndGiff()
    {
        var winnerName = "";
        if(player1Score > player2Score )
            winnerName = player1Name;
        else if( player1Score<player2Score)
            winnerName = player2Name;

        val winnerFragment = WinnerPopupFragment.newInstance(winnerName)
        supportFragmentManager.beginTransaction()
            .replace(R.id.winnerFragment, winnerFragment)
            .commit()

        updateViewsVisibility(false)
        resetGameHistory()
    }

    private fun setUserNameUpdater(){
        player2ScoreTextView.setOnClickListener {
            showEditNameDialog()
        }
    }
    private fun showEditNameDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_name, null)
        val editTextName = dialogView.findViewById<EditText>(R.id.editTextName)
        editTextName.setText(player2Name)

        val dialogBuilder = androidx.appcompat.app.AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Edit Name")
            .setPositiveButton("Save") { _, _ ->
                val newName = editTextName.text.toString()
                updateName(newName)
            }
            .setNegativeButton("Cancel", null)
            .create()
        dialogBuilder.show()
    }

    private fun updateName(newName: String) {
        val user = User(
            id = player2Id,
            userName = newName,
            isExternal = false,
        )
        userRepository.updateUser(user)
        player2Name = newName;
        updateDynamicViewValues()
    }
}