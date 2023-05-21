package com.example.tictactoe.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tictactoe.Models.GameHistoryWithUser
import com.example.tictactoe.R

class GameHistoryAdapter(
    private val gameHistoryList: List<GameHistoryWithUser>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<GameHistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_game_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val gameHistory = gameHistoryList[position]
        holder.bind(gameHistory)
    }

    override fun getItemCount(): Int {
        return gameHistoryList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textUser1Score: TextView = itemView.findViewById(R.id.textUser1Score)
        private val textUser2Score: TextView = itemView.findViewById(R.id.textUser2Score)
        private val textUser2: TextView = itemView.findViewById(R.id.textuser2)

        fun bind(gameHistory: GameHistoryWithUser) {
            textUser1Score.text = gameHistory.user1Score.toString()
            textUser2Score.text = gameHistory.user2Score.toString()
            textUser2.text = gameHistory.user2Name
        }

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val gameHistory = gameHistoryList[position]
                    onItemClickListener.onItemClick(gameHistory)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(gameHistory: GameHistoryWithUser)
    }
}
