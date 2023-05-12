package com.example.tictactoe

import BoardFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class OfflineGameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offline_game)

        // Invoke the BoardFragment
        val fragment = BoardFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, fragment)
            .commit()
    }
}