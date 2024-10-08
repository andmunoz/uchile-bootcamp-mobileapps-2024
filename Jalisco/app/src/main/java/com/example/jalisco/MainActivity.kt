package com.example.jalisco

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val name = intent.getStringExtra("name")
        Toast.makeText(this, "Bienvenido $name", Toast.LENGTH_SHORT).show()
    }

    fun playButtonClicked(view: View) {
        val playerNumberEditText = findViewById<EditText>(R.id.numberPlayedTextView)
        val playedNumber = playerNumberEditText.text.toString().toInt()
        val jaliscoNumber = playedNumber + 1

        val answerTextView = findViewById<TextView>(R.id.welcomeTextView)
        answerTextView.text = "Ja ja ja... Yo te gano con el $jaliscoNumber..."
    }

    fun backButtonClicked(view: View) {
        finish()
    }
}
