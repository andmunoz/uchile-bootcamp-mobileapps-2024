package com.example.milistadecompras.helpers

import java.io.BufferedWriter
import java.net.HttpURLConnection
import java.net.URL

class WebServiceHelper(private val baseUrl: String) {
    // Declaración de variables
    private lateinit var connection: HttpURLConnection

    fun makeGETRequest(resource: String): String? {
        // Crear y abrir la conexión al servicio
        val url = URL(this.baseUrl + resource)
        connection = url.openConnection() as HttpURLConnection

        // Establecer el método de solicitud y realizar la conexión
        connection.requestMethod = "GET"
        connection.connectTimeout = 5000
        connection.readTimeout = 5000

        try {
            // Realizar la conexión
            connection.connect()

            // Leer la respuesta del servicio
            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Convertir la respuesta a una cadena
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                connection.disconnect()
                return response
            } else {
                // Manejar errores de respuesta
                connection.disconnect()
                return null
            }
        } catch (e: Exception) {
            // Manejar errores de conexión
            e.printStackTrace()
            connection.disconnect()
            return null
        }
    }

    fun makePOSTRequest(resource: String, data: String): String? {
        // Crear y abrir la conexión al servicio
        val url = URL(this.baseUrl + resource)
        connection = url.openConnection() as HttpURLConnection

        // Configuramos los encabezados
        connection.setRequestProperty("Content-Type", "application/json")
        connection.setRequestProperty("Accept", "application/json")
        connection.requestMethod = "POST"
        connection.doOutput = true
        connection.connectTimeout = 5000
        connection.readTimeout = 5000

        try {
            // Realizar la conexión
            connection.outputStream.use { outputStream ->
                BufferedWriter(outputStream.writer()).use { writer ->
                    writer.write(data)
                    writer.flush()
                }
            }

            // Verificar respuesta del servicio
            val responseCode = connection.responseCode

            if (responseCode == HttpURLConnection.HTTP_CREATED) {
                connection.disconnect()
                return null
            }
            else {
                connection.disconnect()
                return null
            }
        } catch (e: Exception) {
            // Manejar errores de escritura
            e.printStackTrace()
            connection.disconnect()
            return null

        }
    }

    fun makePUTRequest(resource: String, data: String): String? {
        // TODO: Queda pendiente de implementar
        return null
    }

    fun makeDELETERequest(resource: String): String? {
        // TODO: Queda pendiente de implementar
        return null
    }
}