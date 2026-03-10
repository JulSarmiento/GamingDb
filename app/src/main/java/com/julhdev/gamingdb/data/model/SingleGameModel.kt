package com.julhdev.gamingdb.data.model

import com.google.gson.annotations.SerializedName

/**
 * Modelo de datos que representa los detalles de un solo juego.
 * @property name Nombre del juego.
 * @property description Descripción detallada del juego.
 * @property metacritic Puntuación de Metacritic del juego.
 * @property website Sitio web oficial del juego.
 * @property backgroundImage URL de la imagen de fondo del juego.
 * @usage SingleGameModel(name = "Game Name", description = "Game Description", metacritic = 85, website = "https://gamewebsite.com", backgroundImage = "https://imageurl.com/image.jpg")
 */
data class SingleGameModel(

    val name: String,

    @SerializedName("description_raw")
    val description: String,

    val metacritic: Int,
    val website: String,

    @SerializedName("background_image")
    val backgroundImage: String,

)
