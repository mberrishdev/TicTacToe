package com.example.tictactoe

import BoardFragment
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class HomePageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        showWelcomeName()
        offlineButton()
    }

    fun showWelcomeName(){
        val greetingTextView = findViewById<TextView>(R.id.welcomeTextView)
        val name = intent.getStringExtra("name")
        greetingTextView.text = "Welcome, $name!"
    }

    fun offlineButton(){
        val offButton = findViewById<Button>(R.id.offlineButton)
        offButton.setOnClickListener {
            val intent = Intent(this, OfflineGameActivity::class.java)
            startActivity(intent)
        }
    }
}