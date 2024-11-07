package com.example.fragmentexample

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), BlankFragmentB.OnMessageSendListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun changeFragment(view: View) {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (fragment is BlankFragmentA) {
            val blankFragmentWithMessage = BlankFragmentB.newInstance("Hola desde el Activity Main")
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, blankFragmentWithMessage)
                .addToBackStack(null)
                .commit()
        }
        else {
            supportFragmentManager.popBackStack()
        }
    }

    fun showAlert(view: View) {
        val alertFragment = AlertFragment()
        alertFragment.show(supportFragmentManager, "alert")
    }

    fun showForm(view: View) {
        val dialog = FormFragment()
        dialog.show(supportFragmentManager, "form")
    }

    override fun onMessageSend(message: String) {
        Toast.makeText(this, "Mensaje recibido: $message", Toast.LENGTH_SHORT).show()
    }

}
