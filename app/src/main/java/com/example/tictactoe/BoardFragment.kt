import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.tictactoe.R

class BoardFragment : Fragment() {

    private lateinit var buttons: Array<Button>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_board, container, false)
        setupButtons(view)
        return view
    }

    private fun setupButtons(view: View) {
        buttons = Array(9) { index ->
            view.findViewById(resources.getIdentifier("button${index + 1}", "id", activity?.packageName))
        }

        buttons.forEach { button ->
            button.setOnClickListener {
                button.text = "X"
                Toast.makeText(activity, "Button ${button.text} clicked", Toast.LENGTH_SHORT).show()
                // Perform your game logic here
            }
        }
    }
}
