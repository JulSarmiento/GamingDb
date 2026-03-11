package com.julhdev.gamingdb.util

import com.julhdev.gamingdb.BuildConfig

/**
 * Objeto que contiene constantes utilizadas en la aplicación.
 * Incluye URLs base, endpoints y claves de API.
 * @see BuildConfig
 * @usage Utiliza estas constantes para configurar solicitudes de red y acceder a la API.
 */
object Constants {

    const val BASE_URL = BuildConfig.BASE_URL

    const val ENDPOINT = BuildConfig.ENDPOINT_GAMES

    const val API_KEY = BuildConfig.API_KEY
}