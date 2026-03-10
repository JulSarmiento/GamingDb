package com.julhdev.gamingdb.data.repository

import com.julhdev.gamingdb.data.api.GameApi
import com.julhdev.gamingdb.data.model.GameList
import com.julhdev.gamingdb.data.model.SingleGameModel
import com.julhdev.gamingdb.util.resource.Resource
import com.julhdev.gamingdb.util.safeCallApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Repositorio para manejar las operaciones relacionadas con los juegos.
 * @param gameApi La instancia de GamesApi para realizar llamadas a la API.
 * @see GameApi
 * @usage Inyectar GamesRepository en ViewModels o componentes de UI para acceder a datos de juegos.
 */
class GamesRepository @Inject constructor(
    private val gameApi: GameApi
) {

    /**
     * Obtiene la lista de juegos desde la API.
     * @return Un objeto Resource que contiene la lista de juegos o un mensaje de error.
     */
    fun getGames(filter: String? = null ): Flow<Resource<List<GameList>>> = flow {
        emit(Resource.Loading())
        when (val result = safeCallApi{ gameApi.getGames(filter) }) {
            is Resource.Success -> {
                val gamesList: List<GameList> = result.data?.results ?: emptyList()
                emit(Resource.Success(gamesList))
            }
            is Resource.Error -> {
                emit(Resource.Error(result.message))
            }
            is Resource.Loading -> {
                emit(Resource.Loading())
            }
        }
    }

    /**
     * Obtiene los detalles de un juego específico por su ID.
     * @param id El ID del juego a obtener.
     * @return Un objeto Resource que contiene los detalles del juego o un mensaje de error.
     */
    fun getGameById(id: Int): Flow<Resource<SingleGameModel>> = flow {
        emit(Resource.Loading())
        when (val result = safeCallApi { gameApi.getGameById(id) }) {
            is Resource.Success -> {
                val gameDetails: SingleGameModel = result.data ?: SingleGameModel(
                    name = "N/A",
                    description = "N/A",
                    metacritic = 0,
                    website = "N/A",
                    backgroundImage = "N/A"
                )
                emit(Resource.Success(gameDetails))
            }
            is Resource.Error -> {
                emit(Resource.Error(result.message))
            }
            is Resource.Loading -> {
                emit(Resource.Loading())
            }
        }
    }
}

