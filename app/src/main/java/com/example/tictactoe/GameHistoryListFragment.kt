package com.example.tictactoe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tictactoe.Adapters.GameHistoryAdapter
import com.example.tictactoe.Database.AppDatabase
import com.example.tictactoe.Database.GamesHisory.GameHistory
import com.example.tictactoe.Database.GamesHisory.GameHistoryRepository
import com.example.tictactoe.Database.Users.User
import com.example.tictactoe.Database.Users.UserRepository
import com.example.tictactoe.Models.GameHistoryWithUser
import kotlinx.coroutines.launch

class GameHistoryListFragment : Fragment(),GameHistoryAdapter.OnItemClickListener {

    private var onItemClickListener: AdapterView.OnItemClickListener? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var listView: ListView
    private lateinit var gameHistoryList: List<GameHistoryWithUser>
    private lateinit var player1Name: String
    private lateinit var player2Name: String
    private var player1Id: Long = 0
    private var gameHistoryId: Long = 0

    private lateinit var userRepository: UserRepository
    private lateinit var gameHistoryRepository: GameHistoryRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game_history_list, container, false)

        listView = view.findViewById(R.id.listView)
        player1Name = arguments?.getString("Player1Name") ?: ""
        player1Id = arguments?.getLong("Player1Id") ?: 0

        val userDao = AppDatabase.getInstance(requireContext()).userDao()
        userRepository = UserRepository(userDao)

        val gameHistoryDao = AppDatabase.getInstance(requireContext()).gameHistoryDao()
        gameHistoryRepository = GameHistoryRepository(gameHistoryDao)

        loadUserGameHistory()

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = GameHistoryAdapter(gameHistoryList, this)

        val saveAndPlayButton = view.findViewById<Button>(R.id.saveAndPlayButton)
        saveAndPlayButton.setOnClickListener {
            val nameEditText = view.findViewById<EditText>(R.id.userName)
            player2Name = nameEditText.text.toString()
            createUser(player2Name)
            createGameHistory()
            handlerItemClickOnOfflineGameActivity(gameHistoryId)
        }

        return view
    }

    private fun loadUserGameHistory()
    {
        val gameHistoryListWithUser = mutableListOf<GameHistoryWithUser>()
        val gameHistoryListFromDb = gameHistoryRepository.getGameHistoryByUser1(player1Id)

        gameHistoryListFromDb.forEach { gh ->
            val userName = userRepository.getUserNameById(gh.user2Id)?:""
            val gameHistoryWithUser = GameHistoryWithUser(gh.id, gh.user1Score, gh.user2Score, userName)
            gameHistoryListWithUser.add(gameHistoryWithUser)
        }
        gameHistoryList = gameHistoryListWithUser
    }

    override fun onItemClick(gameHistory: GameHistoryWithUser) {
        val gameHistoryId = gameHistory.id
        handlerItemClickOnOfflineGameActivity(gameHistoryId)
    }

    private fun createUser(name: String)
    {
        val newUser = User(
            userName = name,
            isExternal = true
        )
        userRepository.insertUser(newUser)
    }

    private fun createGameHistory() {
            val player1Id = userRepository.getUserIdByName(player1Name)
        val player2Id = userRepository.getUserIdByName(player2Name)

        if(player1Id !=null && player2Id !=null) {
                val gameHistory = GameHistory(
                    user1Id = player1Id,
                    user2Id = player2Id,
                    user1Score = 0,
                    user2Score = 0,
                )

                gameHistoryId = gameHistoryRepository.insertGameHistory(gameHistory)
            }
    }

    private fun handlerItemClickOnOfflineGameActivity(gameHistoryId:Long){
        (requireActivity() as OfflineGameActivity).handleItemClick(gameHistoryId)
    }
}
