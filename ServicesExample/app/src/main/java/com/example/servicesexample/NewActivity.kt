package com.example.servicesexample

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.servicesexample.httpurlconnection.WebServiceHelper
import com.example.servicesexample.retrofit.Post
import com.example.servicesexample.retrofit.WebServiceController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewActivity : AppCompatActivity() {
    // Declaramos los widgets de la vista
    private lateinit var endpointInput: EditText
    private lateinit var getterInput: EditText
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
        endpointInput = findViewById(R.id.endpoint_input)
        getterInput = findViewById(R.id.getter_input)
        titleInput = findViewById(R.id.title_input)
        userInput = findViewById(R.id.user_input)
        bodyInput = findViewById(R.id.body_input)
        saveButton = findViewById(R.id.save_button)
        cancelButton = findViewById(R.id.back_button)

        // Obtenemos los datos del intent
        val endpoint = intent.getStringExtra("endpoint")
        endpointInput.setText(endpoint)
        val getter = intent.getStringExtra("getter")
        getterInput.setText(getter)

        // Configuramos los listeners de los botones
        saveButton.setOnClickListener {
            Toast.makeText(this, "Guardando cambios con ${getterInput.text}", Toast.LENGTH_SHORT).show()
            if (getterInput.text.toString() == "HttpURLConnection") {
                onSaveElementWithHttpUrlConnection()
            } else if (getterInput.text.toString() == "Retrofit") {
                onSaveElementWithRetrofit()
            }
        }
        cancelButton.setOnClickListener { onCancel() }
    }

    fun onCancel() {
        // Volver a la actividad anterior
        finish()
    }

    fun onSaveElementWithHttpUrlConnection() {
        // Creamos y abrimo la conexión al endpoint
        val endpoint = endpointInput.text.toString()
        val service = "posts"
        val webServiceHelper = WebServiceHelper(endpoint)

        // Obtener los valores de los campos de entrada
        val title = titleInput.text.toString()
        val userId = userInput.text.toString().toInt()
        val body = bodyInput.text.toString()

        // Crear el objeto JSON
        val jsonString = """
            {
                "title": "$title",
                "userId": $userId,
                "body": "$body"
            }
        """.trimIndent()

        // Enviar los datos al endpoint y mostrar la respuesta de la llamada
        var response = ""
        CoroutineScope(Dispatchers.IO).launch {
            response = webServiceHelper.sendPUTRequest(endpoint + service, jsonString)
            Log.d("HttpURLConnection", response)
        }
    }

    fun onSaveElementWithRetrofit() {
        // Creamos y abrimo la conexión al endpoint
        val endpoint = endpointInput.text.toString()
        val webServiceController = WebServiceController(endpoint)

        // Obtener los valores de los campos de entrada
        val title = titleInput.text.toString()
        val userId = userInput.text.toString().toInt()
        val body = bodyInput.text.toString()

        // Crear el objeto JSON
        val post = Post(title, userId, body)

        // Enviar los datos al endpoint y mostrar la respuesta de la llamada
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = webServiceController.createPost(post).toString()
                Log.d("Retrofit", response)
            }
            catch (e: Exception) {
                Log.d("Retrofit", "Error al procesar el servicio: ${e.message}")
            }
        }
    }
}