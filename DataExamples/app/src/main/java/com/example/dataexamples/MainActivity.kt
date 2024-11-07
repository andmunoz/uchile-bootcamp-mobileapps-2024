package com.example.dataexamples

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    lateinit var dataStore: UserDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dataStore = UserDataStore(this)

        lifecycleScope.launch {
            dataStore.setId(1)
            dataStore.setUsername("Juan Pérez")
            dataStore.setEmail("jperez@gmail.com")
            dataStore.setToken("************")
            println("Email Saved!")
        }

        val id = runBlocking { dataStore.getId().first() }
        val username = runBlocking { dataStore.getUsername().first() }
        val email = runBlocking { dataStore.getEmail().first() }
        val token = runBlocking { dataStore.getToken().first() }

        println("id: $id")
        println("username: $username")
        println("email: $email")
        println("token: $token")

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "user-database"
        ).build()

        val userDao = db.userDao()

        lifecycleScope.launch {
            val user = User(1, "Juan", "Pérez")
            userDao.insertAll(user)
        }

        lifecycleScope.launch {
            val users: List<User> = userDao.getAll()
            for (user in users) {
                println(user)
            }
        }
    }

}
