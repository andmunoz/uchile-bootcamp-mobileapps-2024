package com.example.fragmentexample

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment

class FormFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())

        // Inflar el layout del formulario
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.fragment_form, null)

        // Configurar acciones del botón
        val submitButton = view.findViewById<Button>(R.id.submitButton)
        submitButton.setOnClickListener {
            processForm(view)
        }

        // Configurar el builder con el layout inflado
        builder.setView(view)

        return builder.create()
    }

    fun processForm(view: View) {
        val nameInput = view.findViewById<EditText>(R.id.nameInput)
        val emailInput = view.findViewById<EditText>(R.id.emailInput)

        val name = nameInput.text.toString()
        val email = emailInput.text.toString()

        Toast.makeText(context, "Nombre: $name\nEmail: $email", Toast.LENGTH_SHORT).show()
        dismiss()  // Cierra el diálogo
    }
}

