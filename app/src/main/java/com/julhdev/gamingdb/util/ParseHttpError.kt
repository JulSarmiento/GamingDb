package com.julhdev.gamingdb.util

import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.HttpException

/**
  * Función para parsear el mensaje de error de una excepción HttpException
  * Retorna el mensaje de error si está disponible, o null si no se puede parsear
  * @param e La excepción HttpException a parsear
  * @return El mensaje de error como String o null
  * @usage Utilizar esta función dentro de bloques catch para obtener mensajes de error detallados
 */
fun parseHttpError(e: HttpException): String? {
    return try {
        val errorBody: ResponseBody? = e.response()?.errorBody()
        val json = errorBody?.string() ?: return null
        val map = Gson().fromJson(json, Map::class.java)
        map?.get("message") as? String
    } catch (_: Exception) {
        null
    }
}