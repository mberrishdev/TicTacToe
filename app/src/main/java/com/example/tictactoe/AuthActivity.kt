package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.tictactoe.Database.AppDatabase
import com.example.tictactoe.Database.Users.User
import com.example.tictactoe.Database.Users.UserRepository

class AuthActivity : AppCompatActivity() {
    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        val userDao = AppDatabase.getInstance(applicationContext).userDao()
        userRepository = UserRepository(userDao)

        val isInternalUserExist = userRepository.checkIfInternalUserExist()

        if (isInternalUserExist) {
            val name = userRepository.getInternalUserName()
            redirectToHomePage(name)
        } else {
            setContentView(R.layout.activity_auth)
            val nextButton = findViewById<Button>(R.id.nextButton)
            nextButton.setOnClickListener {
                val nameEditText = findViewById<EditText>(R.id.nameEditText)
                val name = nameEditText.text.toString()
                createInternalUser(name)
                redirectToHomePage(name)
            }
        }
    }

    private fun redirectToHomePage(name:String){
        val intent = Intent(this, HomePageActivity::class.java)
        intent.putExtra("name", name)
        startActivity(intent)
    }

    private fun createInternalUser(name:String)
    {
        val newUser = User(
            userName = name,
            isExternal = false
        )

        userRepository.insertUser(newUser)
    }
}