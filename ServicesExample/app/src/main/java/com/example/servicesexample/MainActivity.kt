package com.example.servicesexample

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.servicesexample.httpurlconnection.WebServiceHelper
import com.example.servicesexample.retrofit.WebServiceController
import com.example.servicesexample.volley.WebServiceQueue
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// Definimos la URL Base de la API
const val BASE_URL = "https://jsonplaceholder.typicode.com/"

class MainActivity : AppCompatActivity() {
    // Declaramos los widgets de la vista
    private lateinit var endpointInput: EditText
    private lateinit var getterSpinner: Spinner
    private lateinit var jsonResponseText: EditText
    private lateinit var loadButton: Button
    private lateinit var newButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        // Llamamos al método onCreate de la superclase
        super.onCreate(savedInstanceState)

        // Configuramos el modo de edge-to-edge
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializamos los widgets
        endpointInput = findViewById(R.id.endpoint_input)
        getterSpinner = findViewById(R.id.getter_spinner)
        jsonResponseText = findViewById(R.id.json_response_text)
        loadButton = findViewById(R.id.load_button)
        newButton = findViewById(R.id.new_button)

        // Configuramos el texto del endpoint
        endpointInput.setText(BASE_URL)

        // Configuramos el listener del botón
        loadButton.setOnClickListener {
            val selectedOption = getterSpinner.selectedItem.toString()
            Toast.makeText(this, "Obteniendo datos con $selectedOption", Toast.LENGTH_SHORT).show()
            if (selectedOption == "HttpURLConnection") {
                onLoadFromHttpUrlConnection()
            } else if (selectedOption == "Retrofit") {
                onLoadFromRetrofit()
            } else if (selectedOption == "Volley") {
                onLoadFromVolley()
            }
        }
        newButton.setOnClickListener { onNewElement() }

        // Llenamos el spinner con las opciones de obtención de datos
        val getterOptions = resources.getStringArray(R.array.getter_options)
        getterSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, getterOptions)
    }

    fun onNewElement() {
        val intent = Intent(this, NewActivity::class.java)
        intent.putExtra("endpoint", endpointInput.text.toString())
        intent.putExtra("getter", getterSpinner.selectedItem.toString())
        startActivity(intent)
    }

    fun onLoadFromHttpUrlConnection() {
        // Obtenemos la URL del endpoint
        val endpoint = endpointInput.text.toString()
        val service = "posts"
        val webServiceHelper = WebServiceHelper(endpoint)

        // Ponemos un estado de carga en el texto de respuesta
        jsonResponseText.setText("Cargando datos de la URL: $endpoint/$service")

        // Llamamos a la función de manera asíncrona para obtener los datos del endpoint
        CoroutineScope(Dispatchers.IO).launch {
            val response = webServiceHelper.sendGETRequest(service)
            withContext(Dispatchers.Main) {
                jsonResponseText.setText(response)
            }
            Log.d("HttpURLConnection", response.toString())
        }
    }

    fun onLoadFromRetrofit() {
        // Obtenemos la URL del endpoint
        val endpoint = endpointInput.text.toString()
        val webServiceController = WebServiceController(endpoint)

        // Ponemos un estado de carga en el texto de respuesta
        jsonResponseText.setText("Cargando datos de la URL: $endpoint")

        // Llamamos a la función de manera asíncrona para obtener los datos del endpoint
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = webServiceController.getPosts()
                if (response.isSuccessful) {
                    val posts = response.body()
                    withContext(Dispatchers.Main) {
                        jsonResponseText.setText(posts.toString())
                    }
                }
                else {
                    jsonResponseText.setText("Error al cargar los datos de la URL: ${response.code()}")
                }
                Log.d("Retrofit", response.toString())
            } catch (e: Exception) {
                jsonResponseText.setText("Error al procesar el servicio: ${e.message}")
            }
        }
    }

    fun onLoadFromVolley() {
        // Obtenemos la URL del endpoint
        val endpoint = endpointInput.text.toString()
        val webServiceQueue = WebServiceQueue(endpoint, this)

        // Ponemos un estado de carga en el texto de respuesta
        jsonResponseText.setText("Cargando datos de la URL: $endpoint")

        // Llamamos a la función de manera asíncrona para obtener los datos del endpoint
        webServiceQueue.getData("posts", { response ->
            jsonResponseText.setText(response)
        })
    }
}