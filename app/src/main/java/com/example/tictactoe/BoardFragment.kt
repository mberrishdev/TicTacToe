import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.tictactoe.R

class BoardFragment : Fragment() {

    // interface fot ButtonClickEvent
    interface BoardFragmentListener {
        fun onButtonClick(row: Int, col: Int,)
        fun onWin(playerSymbol: String)
    }

    private var boardFragmentListener: BoardFragmentListener? = null

    // Define the singleton instance variable
    companion object {
        private var instance: BoardFragment? = null

        fun getInstance(): BoardFragment {
            if (instance == null) {
                instance = BoardFragment()
            }
            return instance as BoardFragment
        }
    }

    fun setBoardFragmentListener(listener: BoardFragmentListener) {
        boardFragmentListener = listener
    }


    enum class Player(val symbol: String) {
        X("X"),
        O("O")
    }

    private lateinit var buttons: Array<Array<Button>>

    var currentPlayer = Player.X
    var movesPlayed = 0
    private var gameEnded = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_board, container, false)
        initializeButtons(view)
        initializeGame()

        return view
    }

    private fun initializeButtons(view: View) {
        buttons = Array(3) { row ->
            Array(3) { col ->
                view.findViewById<Button>(resources.getIdentifier(
                    "btn_${row}_$col",
                    "id",
                    requireActivity().packageName
                )).apply {
                    setOnClickListener { onButtonClicked(row, col, this) }
                }
            }
        }
    }

    private fun initializeGame() {
        currentPlayer = Player.X
        movesPlayed = 0
        gameEnded = false
        clearButtons()
    }

    fun resetGame()
    {
        initializeGame()
    }

    fun clickButton(row: Int, col: Int)
    {
        val button = buttons.get(row).get(col)
        onButtonClicked(row, col, button)
    }

    private fun onButtonClicked(row: Int, col: Int, button: Button) {
        if (!gameEnded && button.text.isEmpty()) {
            button.text = currentPlayer.symbol
            movesPlayed++

            if (checkWin(row, col)) {
                gameEnded = true
                showWinMessage()
            } else if (movesPlayed == 9) {
                gameEnded = true
                showDrawMessage()
            } else {
                switchPlayer()
            }
        }

        boardFragmentListener?.onButtonClick(row,col)
    }

    private fun checkWin(row: Int, col: Int): Boolean {
        // Check row
        if (buttons[row][0].text == buttons[row][1].text &&
            buttons[row][0].text == buttons[row][2].text
        ) {
            return true
        }

        // Check column
        if (buttons[0][col].text == buttons[1][col].text &&
            buttons[0][col].text == buttons[2][col].text
        ) {
            return true
        }

        // Check diagonal
        if (row == col || row + col == 2) {
            if (buttons[0][0].text == buttons[1][1].text &&
                buttons[0][0].text == buttons[2][2].text &&
                buttons[0][2].text.isNotEmpty()
            ) {
                return true
            }

            if (buttons[0][2].text == buttons[1][1].text &&
                buttons[0][2].text == buttons[2][0].text &&
                buttons[0][2].text.isNotEmpty()
            ) {
                return true
            }
        }

        return false
    }

    private fun switchPlayer() {
        currentPlayer = if (currentPlayer == Player.X) Player.O else Player.X
    }

    private fun clearButtons() {
        for (row in buttons) {
            for (button in row) {
                button.text = ""
            }
        }
    }

    private fun showWinMessage() {
        boardFragmentListener?.onWin(currentPlayer.symbol)
        val message = getString(R.string.player_wins, currentPlayer.symbol)
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showDrawMessage() {
        val message = getString(R.string.draw_message)
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

}
