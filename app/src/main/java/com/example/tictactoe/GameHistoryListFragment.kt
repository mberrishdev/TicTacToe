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
    private var player1Name: String = "";
    private var player2Name: String = "";
    private var player1Id: Long = 0;
    private var gameHistoryId: Long = 0;

    lateinit var userRepository: UserRepository
        private set
    lateinit var gameHistoryRepository: GameHistoryRepository
        private set

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game_history_list, container, false)

        listView = view.findViewById(R.id.listView)
        player1Name = arguments?.getString("Player1Name") ?: ""
        player1Id = arguments?.getLong("Player1Id") ?: 0

        player1Name = "test"
        player1Id = 1;
        //listView = view.findViewById(R.id.listView)

        val userDao = AppDatabase.getInstance(requireContext()).userDao()
        userRepository = UserRepository(userDao)

        val gameHistoryDao = AppDatabase.getInstance(requireContext()).gameHistoryDao()
        gameHistoryRepository = GameHistoryRepository(gameHistoryDao)

       loadUserGameHistory();

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = GameHistoryAdapter(gameHistoryList,this);

        /*
       // Create a list of items
       val itemList = listOf("Item 1", "Item 2", "Item 3")

       // Create an adapter to populate the list view
       val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, itemList)
       listView.adapter = adapter
        */

        val saveAndPlayButton = view.findViewById<Button>(R.id.saveAndPlayButton)
        saveAndPlayButton.setOnClickListener {
            val nameEditText = view.findViewById<EditText>(R.id.userName);
            player2Name = nameEditText.text.toString()
            createUser(player2Name);
            createGameHistory();
            handlerItemClickOnOfflineGameActivity(gameHistoryId)
        }

        return view
    }

    private fun loadUserGameHistory()
    {
        val gameHistoryListWithUser = mutableListOf<GameHistoryWithUser>();
        val gameHistoryListFromDb = gameHistoryRepository.getGameHistoryByUser1(player1Id);

        gameHistoryListFromDb.forEach { gh ->
            val userName = userRepository.getUserNameById(gh.user2Id)?:"";
            val gameHistoryWithUser = GameHistoryWithUser(gh.id, gh.user1Score, gh.user1Score,userName)
            gameHistoryListWithUser.add(gameHistoryWithUser);
        }
        gameHistoryList = gameHistoryListWithUser;
    }

    override fun onItemClick(gameHistory: GameHistoryWithUser) {
        val gameHistoryId = gameHistory.id
        handlerItemClickOnOfflineGameActivity(gameHistoryId);
    }

    private fun createUser(name: String)
    {
        val newUser = User(
            userName = name,
            isExternal = true
        )
        userRepository.insertUser(newUser);
    }

    private fun createGameHistory() {
        //lifecycleScope.launch() {
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


       // }
    }

    private fun handlerItemClickOnOfflineGameActivity(gameHistoryId:Long){
        (requireActivity() as OfflineGameActivity).handleItemClick(gameHistoryId)
    }
}
