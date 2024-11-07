package com.example.fragmentexample

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class AlertFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("Confirmación")
            .setMessage("¿Estás seguro de que deseas continuar?")
            .setPositiveButton("Sí") { _, _ ->
                // Acción positiva
                Toast.makeText(context, "Confirmado", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No", null)  // No se hace nada si se cancela
            .create()
    }
}

