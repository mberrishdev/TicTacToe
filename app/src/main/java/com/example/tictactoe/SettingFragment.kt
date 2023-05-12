import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.example.tictactoe.R

class SettingFragment : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)

        // Close button click listener
        val imgClose: ImageView = view.findViewById(R.id.imgClose)
        imgClose.setOnClickListener {
            dismiss() // Close the fragment
        }

        // Reset button click listener
        val btnReset: Button = view.findViewById(R.id.btnReset)
        btnReset.setOnClickListener {
            // Add your reset functionality here
        }

        return view
    }
}
