package com.example.jalisco

import android.os.Bundle
import android.view.View
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
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/

        val name = intent.getStringExtra("nombre")
        Toast.makeText(this, "Bienvenido $name", Toast.LENGTH_LONG).show()
    }

    fun onPlayButtonClick(view: View) {
        val numberEditText = findViewById<EditText>(R.id.numberEditText)
        val number = numberEditText.text.toString().toInt()
        val myNumber = number + 1
        val welcomeMessageTextView = findViewById<TextView>(R.id.welcomeMessageTextView)
        welcomeMessageTextView.text = "Ja ja ja. Te gané con el $myNumber"
    }

    fun onQuitButtonClick(view: View) {
        finish()
    }
}