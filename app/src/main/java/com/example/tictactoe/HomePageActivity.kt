package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class HomePageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        showWelcomeName()
        offlineButton()
        playComputerButton()
        settingButton()
    }

    private fun showWelcomeName(){
        val greetingTextView = findViewById<TextView>(R.id.welcomeTextView)
        val name = intent.getStringExtra("name")
        greetingTextView.text = "Welcome, $name!"
    }

    private fun offlineButton(){
        val offButton = findViewById<Button>(R.id.offlineButton)
        offButton.setOnClickListener {
            val intent = Intent(this, OfflineGameActivity::class.java)
            startActivity(intent)
        }
    }

    private fun playComputerButton(){
        val aiButton = findViewById<Button>(R.id.playComputerButton)
        aiButton.setOnClickListener {
            //val intent = Intent(this, AiGameActivity::class.java)
            //startActivity(intent)
        }
    }

    private fun settingButton(){
        val setButton = findViewById<Button>(R.id.settingsButton)
        setButton.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }
    }
}