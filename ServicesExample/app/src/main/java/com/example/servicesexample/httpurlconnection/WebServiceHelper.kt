package com.example.servicesexample.httpurlconnection

import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class WebServiceHelper(val baseUrl: String) {
    suspend fun sendGETRequest(endpoint: String): String? {
        // Creamos y abrimo la conexión al endpoint
        val url = URL(baseUrl + endpoint)
        val connection = url.openConnection() as HttpURLConnection

        // Configuramos la conexión
        connection.requestMethod = "GET"
        connection.connectTimeout = 5000
        connection.readTimeout = 5000

        try {
            // Obtenemos el código de respuesta
            val responseCode = connection.responseCode

            // Si la respuesta es exitosa, obtenemos los datos
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val inputStream = connection.inputStream
                val response = inputStream.bufferedReader().use { it.readText() }

                // Cerramos la conexión
                connection.disconnect()

                // Devolvemos la respuesta
                return response
            }
            // Si la respuesta no es exitosa, mostramos un mensaje de error
            else {
                // Cerramos la conexión
                connection.disconnect()

                // Devolvemos un mensaje de error
                return "Error al cargar los datos de la URL: $responseCode"
            }
        } catch (e: Exception) {
            // Cerramos la conexión
            connection.disconnect()

            // Devolvemos un mensaje de error
            return "Error al procesar el servicio: ${e.message}"
        }
    }

    suspend fun sendPUTRequest(endpoint: String, jsonString: String): String {
        // Crear la conexión HTTP
        val url = URL(baseUrl +     endpoint)
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
                    writer.write(jsonString)
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

}