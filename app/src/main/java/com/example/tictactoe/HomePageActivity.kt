package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.tictactoe.Database.AppDatabase
import com.example.tictactoe.Database.GamesHisory.GameHistory
import com.example.tictactoe.Database.Users.User
import com.example.tictactoe.Database.Users.UserRepository

class HomePageActivity : AppCompatActivity() {

    private var id: Long = 0
    private var name: String? = ""
    private var isExternal: Boolean = true
    private lateinit var userRepository: UserRepository
    private lateinit var welcomeTextVie: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        val userDao = AppDatabase.getInstance(applicationContext).userDao()
        userRepository = UserRepository(userDao)

        welcomeTextVie = findViewById<TextView>(R.id.welcomeTextView)
        welcomeTextVie.setOnClickListener {
            showEditNameDialog()
        }

        id = intent.getLongExtra("id",0)
        name = intent.getStringExtra("name")
        isExternal = intent.getBooleanExtra("isExternal",true)

        if (id.compareTo(0L) == 0)
        {
            val user = userRepository.getInternalUser()
            id = user.id
            name = user.userName
            isExternal = user.isExternal
        }
        showWelcomeName()
        offlineButton()
        settingButton()
    }

    private fun showWelcomeName(){
        welcomeTextVie.text = "Welcome, $name!"
    }

    private fun showEditNameDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_name, null)
        val editTextName = dialogView.findViewById<EditText>(R.id.editTextName)
        editTextName.setText( intent.getStringExtra("name"))

        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Edit Name")
            .setPositiveButton("Save") { _, _ ->
                val newName = editTextName.text.toString()
                updateName(newName)
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialogBuilder.show()
    }

    private fun updateName(newName: String) {
        val user = User(
            id = id,
            userName = newName,
            isExternal = isExternal,
        )

        userRepository.updateUser(user)

        name = newName;
        showWelcomeName()
    }

    private fun offlineButton(){
        val offButton = findViewById<Button>(R.id.offlineButton)
        offButton.setOnClickListener {
            val intent = Intent(this, OfflineGameActivity::class.java)
            startActivity(intent)
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