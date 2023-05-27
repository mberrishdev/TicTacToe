package com.example.tictactoe

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class WinnerPopupFragment : Fragment() {
    companion object {
        private const val ARG_WINNER_NAME = "winner_name"

        fun newInstance(winnerName: String): WinnerPopupFragment {
            val fragment = WinnerPopupFragment()
            val args = Bundle()
            args.putString(ARG_WINNER_NAME, winnerName)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_winner_popup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val winnerName = arguments?.getString(ARG_WINNER_NAME)

        val winnerNameTextView = view.findViewById<TextView>(R.id.winnerTextView)
        winnerNameTextView.text = getString(R.string.winner_text, winnerName)

        val nearestRestaurantButton = view.findViewById<Button>(R.id.nearestRestaurant)
        nearestRestaurantButton.setOnClickListener()
        {
            val googlePlacesUrl = "https://www.google.com/maps/search/restaurants/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(googlePlacesUrl))
            startActivity(intent)
        }
    }
}