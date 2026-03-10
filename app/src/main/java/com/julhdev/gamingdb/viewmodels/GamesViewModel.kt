package com.julhdev.gamingdb.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.julhdev.gamingdb.data.model.GameList
import com.julhdev.gamingdb.data.model.SingleGameModel
import com.julhdev.gamingdb.data.repository.GamesRepository
import com.julhdev.gamingdb.util.resource.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * GamesViewModel es un ViewModel que gestiona el estado y las operaciones relacionadas con la lista de juegos.
 * Interactúa con el GamesRepository para recuperar datos de juegos y expone un StateFlow que representa
 * el estado actual de la lista de juegos, incluyendo estados de carga, éxito y error.
 * @property repository El GamesRepository utilizado para las operaciones de datos.
 * @see GamesRepository
 * @see GameList
 * @see Resource
 * @usage Inyectar GamesViewModel en componentes de UI para observar y manipular datos de juegos.
 */
@HiltViewModel
class GamesViewModel @Inject constructor(
    private val repository: GamesRepository
): ViewModel() {

    private val _games = MutableStateFlow<Resource<List<GameList>>>(Resource.Loading())
    val games = _games.asStateFlow()

    private val _state = MutableStateFlow<Resource<SingleGameModel>>(Resource.Loading())
    val state = _state.asStateFlow()

    /**
     * Limpia el estado actual del StateFlow de detalles del juego estableciéndolo en Resource.Loading().
     * @usage Llamar a cleanState() para restablecer el estado antes de una nueva operación de obtención de datos.
     */
    fun cleanState() {
        _state.value = Resource.Loading()
    }

    /**
     * Recupera la lista de juegos del repositorio y actualiza el StateFlow correspondiente.
     * Maneja los estados de éxito, error y carga utilizando la clase Resource.
     * @see GamesRepository
     * @see Resource
     * @usage Llamar a fetchGames() para iniciar la recuperación de datos de juegos.
     */
    fun fetchGames(filter: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            _games.value = Resource.Loading()
            repository.getGames(filter).collect { result ->
                _games.value = result
            }
        }
    }

    /**
     * Recupera los detalles de un juego específico por su ID y actualiza el StateFlow correspondiente.
     * Maneja los estados de éxito, error y carga utilizando la clase Resource.
     * @param id El ID del juego a obtener.
     * @see GamesRepository
     * @see Resource
     * @usage Llamar a getGameById(id) para iniciar la recuperación de los detalles del juego.
     */
    fun getGameById(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.getGameById(id).collect { result ->
                    _state.value = result
                }
            }
        }
    }
}