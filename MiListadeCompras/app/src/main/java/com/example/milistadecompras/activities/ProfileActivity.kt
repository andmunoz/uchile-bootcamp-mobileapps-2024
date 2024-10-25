package com.example.milistadecompras.activities

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.milistadecompras.R
import com.example.milistadecompras.openhelper.ProfileDataStore
import kotlinx.coroutines.launch
import java.lang.Long.parseLong

class ProfileActivity : AppCompatActivity() {
    lateinit var profileDataStore: ProfileDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        profileDataStore = ProfileDataStore(this)
    }

    fun saveButtonAction(view: View) {
        lifecycleScope.launch {
            val firstName = findViewById<EditText>(R.id.firstNameField).text.toString()
            profileDataStore.setFirstName(firstName)

            val lastName = findViewById<EditText>(R.id.lastNameField).text.toString()
            profileDataStore.setLastName(lastName)

            val emailAddress = findViewById<EditText>(R.id.emailField).text.toString()
            profileDataStore.setEmailAddress(emailAddress)

            val phoneNumber = findViewById<EditText>(R.id.phoneField).text.toString()
            profileDataStore.setPhoneNumber(parseLong(phoneNumber))
        }
    }
}