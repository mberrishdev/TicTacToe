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
            val user = userRepository.getInternalUser()
            redirectToHomePage(user)
        } else {
            setContentView(R.layout.activity_auth)
            val nextButton = findViewById<Button>(R.id.nextButton)
            nextButton.setOnClickListener {
                val nameEditText = findViewById<EditText>(R.id.nameEditText)
                val name = nameEditText.text.toString()
                val newUser = createInternalUser(name)
                redirectToHomePage(newUser)
            }
        }
    }

    private fun redirectToHomePage(user: User){
        val intent = Intent(this, HomePageActivity::class.java)

        intent.putExtra("id", user.id)
        intent.putExtra("name", user.userName)
        intent.putExtra("isExternal", user.isExternal)
        startActivity(intent)
    }

    private fun createInternalUser(name:String):User
    {
        val newUser = User(
            userName = name,
            isExternal = false
        )

        val userId = userRepository.insertUser(newUser)

        val updatedUser = User(
            id = userId,
            userName = newUser.userName,
            isExternal = newUser.isExternal
        )

        return updatedUser
    }
}