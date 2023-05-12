package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tictactoe.Database.AppDatabase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppDatabase.getInstance(applicationContext)

        // Launch the Auth Activity
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }
}