package com.example.tictactoe

import BoardFragment
import SettingFragment
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
        settingButton()
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

    fun settingButton(){
        val setButton = findViewById<Button>(R.id.settingsButton)
        setButton.setOnClickListener {
            val settingFragment = SettingFragment()
            settingFragment.show(supportFragmentManager, "setting")
        }
    }
}