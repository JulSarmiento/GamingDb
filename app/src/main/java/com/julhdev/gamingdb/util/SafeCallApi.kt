package com.julhdev.gamingdb.util

import com.julhdev.gamingdb.util.resource.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException

/**
 * Función de orden superior para manejar llamadas a API de forma segura.
 * Envuelve la llamada a la API y captura excepciones comunes, devolviendo un Resource adecuado.
 * @param apiCall La llamada a la API que se va a ejecutar.
 * @return Un objeto Resource que representa el resultado de la llamada a la API.
 * @usage Utilizar esta función para envolver llamadas a API en repositorios o view models.
 */
suspend fun <T> safeCallApi(
    apiCall: suspend () -> T
): Resource<T> {

    return withContext(Dispatchers.IO) {
        try{
            val result = apiCall()
            Resource.Success(result)
        } catch (e: IOException) {
            Resource.Error("Error de red: ${e.localizedMessage}")
        } catch (e: HttpException) {
            val code = e.code()
            val content = e.message()
            val message = parseHttpError(e) ?: "Error HTTP $code, $content"
            Resource.Error(message)
        }catch (e: Exception) {
            Resource.Error("Error inesperado: ${e.localizedMessage}")
        }
    }
}
