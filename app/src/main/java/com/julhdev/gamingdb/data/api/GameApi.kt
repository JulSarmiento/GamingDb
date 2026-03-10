package com.julhdev.gamingdb.data.api

import com.julhdev.gamingdb.data.model.GamesModel
import com.julhdev.gamingdb.data.model.SingleGameModel
import com.julhdev.gamingdb.util.Constants.ENDPOINT
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interfaz de la API para obtener datos relacionados con juegos.
 * Define los endpoints y métodos HTTP para interactuar con la API de juegos.
 * @usage Inyecta esta interfaz en repositorios o view models para acceder a las operaciones de la API.
 */
interface GameApi {

    /**
     * Obtiene una lista de juegos filtrados por un término de búsqueda.
     * @param filter El término de búsqueda para filtrar los juegos.
     * @return Un objeto [GamesModel] que contiene la lista de juegos filtrados o null si no se obtienen datos.
     */
    @GET(ENDPOINT)
    suspend fun getGames(@Query("search") filter: String? = null ): GamesModel?

    /**
     * Obtiene los detalles de un juego específico por su ID.
     * @param id El ID del juego a obtener.
     * @return Un objeto [SingleGameModel] que contiene los detalles del juego o null si no se encuentra el juego.
     */
    @GET("${ENDPOINT}/{id}")
    suspend fun getGameById(@Path(value = "id") id: Int): SingleGameModel?

}