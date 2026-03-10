package com.julhdev.gamingdb.util

import okhttp3.Interceptor
import okhttp3.Response

/**
 * ApiKeyInterceptor es un interceptor de OkHttp que agrega una clave de API a cada solicitud HTTP.
 * Esto es útil para autenticar solicitudes a servicios web que requieren una clave de API.
 * @param apiKey La clave de API que se agregará a las solicitudes.
 * @see Interceptor
 * @usage Agregar este interceptor a la instancia de OkHttpClient utilizada por Retrofit.
 */
class ApiKeyInterceptor(
    private val apiKey: String
) : Interceptor {

    override fun intercept( chain: Interceptor.Chain): Response {
        val original = chain.request()

        val url = original.url.newBuilder()
            .addQueryParameter("key", apiKey)
            .build()

        val request = original.newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }
}