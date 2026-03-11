package com.julhdev.gamingdb.data.model

import com.google.gson.annotations.SerializedName

/**
 * Modelo de datos que representa la respuesta de la API de juegos.
 * @property count El número total de juegos disponibles.
 * @property results La lista de juegos obtenidos de la API.
 * @usage Utilizar este modelo para mapear la respuesta de la API en el repositorio.
 */
data class GamesModel(
    val count: Int,
    val results: List<GameList>
)

/**
 * Modelo de datos que representa un juego individual.
 * @property id El identificador único del juego.
 * @property name El nombre del juego.
 * @property backgroundImage La URL de la imagen de fondo del juego.
 * @usage Utilizar este modelo para mapear los detalles de cada juego en la lista de juegos.
 */
data class GameList(
    val id: Int,
    val name: String,

    @SerializedName("background_image")
    val backgroundImage: String
)
