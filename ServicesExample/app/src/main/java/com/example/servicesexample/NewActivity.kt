package com.example.servicesexample

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class NewActivity : AppCompatActivity() {
    // Declaramos los widgets de la vista
    private lateinit var titleInput: EditText
    private lateinit var userInput: EditText
    private lateinit var bodyInput: EditText
    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        // Llamamos al método onCreate de la superclase
        super.onCreate(savedInstanceState)

        // Configuramos el modo de edge-to-edge
        enableEdgeToEdge()
        setContentView(R.layout.activity_new)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializamos los widgets
        titleInput = findViewById(R.id.title_input)
        userInput = findViewById(R.id.user_input)
        bodyInput = findViewById(R.id.body_input)
        saveButton = findViewById(R.id.save_button)
        cancelButton = findViewById(R.id.back_button)

        // Configuramos los listeners de los botones
        saveButton.setOnClickListener { onSaveChanges() }
        cancelButton.setOnClickListener { onCancel() }
    }

    private fun sendPUTRequest(title: String, userId: Int, body: String): String {
        // Crear un objeto JSON con los datos
        // val endpoint = "https://jsonplaceholder.typicode.com/posts"
        // val jsonObject = "{\"title\": \"$title\", \"userId\": $userId, \"body\": \"$body\"}"
        val endpoint = "https://ejemplo-firebase-657d0-default-rtdb.firebaseio.com/purchase_lists.json"
        val jsonObject = "{\"id\": $userId, \"name\": \"$title\", \"last_purchase_date\": \"2024-10-15\", \"frequency\": \"$body\", \"products\": []}"

        // Crear la conexión HTTP
        val url = URL(endpoint)
        val connection = url.openConnection() as HttpURLConnection

        // Configurar la petición HTTP POST
        connection.requestMethod = "POST"
        connection.setRequestProperty("Content-Type", "application/json; utf-8")
        connection.setRequestProperty("Accept", "application/json")
        connection.doOutput = true
        connection.connectTimeout = 5000
        connection.readTimeout = 5000

        try {
            // Enviar datos al endpoint
            connection.outputStream.use { outputStream ->
                BufferedWriter(OutputStreamWriter(outputStream, "UTF-8")).use { writer ->
                    writer.write(jsonObject)
                    writer.flush()
                }
            }

            // Leer la respuesta del servidor y decidimos que informar al usuario
            if (connection.responseCode == HttpURLConnection.HTTP_CREATED) {
                return "Cambios guardados: ${connection.responseCode}"
            }
            else {
                return "Error al guardar los cambios: ${connection.responseCode}"
            }
        } finally {
            connection.disconnect()
        }
    }

    private fun onSaveChanges() {
        // Obtener los valores de los campos de entrada
        val title = titleInput.text.toString()
        val userId = userInput.text.toString().toInt()
        val body = bodyInput.text.toString()

        // Enviar los datos al endpoint y mostrar la respuesta de la llamada
        var response = ""
        CoroutineScope(Dispatchers.IO).launch {
            response = sendPUTRequest(title, userId, body)
        }
        Toast.makeText(this, response, Toast.LENGTH_SHORT).show()
    }

    private fun onCancel() {
        // Volver a la actividad anterior
        finish()
    }
}