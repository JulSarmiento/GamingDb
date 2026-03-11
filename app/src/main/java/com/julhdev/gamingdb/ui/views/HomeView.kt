package com.julhdev.gamingdb.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.julhdev.gamingdb.data.model.GameList
import com.julhdev.gamingdb.navigation.Detail
import com.julhdev.gamingdb.navigation.Search
import com.julhdev.gamingdb.ui.components.CardGame
import com.julhdev.gamingdb.ui.components.ErrorState
import com.julhdev.gamingdb.ui.components.LoadingState
import com.julhdev.gamingdb.ui.components.MainTopBar
import com.julhdev.gamingdb.ui.components.NoContent
import com.julhdev.gamingdb.util.resource.Resource
import com.julhdev.gamingdb.viewmodels.GamesViewModel

/**
 * Vista principal que muestra una lista de juegos.
 * @param viewModel ViewModel que proporciona los datos de los juegos.
 * @param navController Controlador de navegación para manejar la navegación entre vistas.
 * @usage HomeView(viewModel = gamesViewModel, navController = navController)
 */
@Composable
fun HomeView(viewModel: GamesViewModel, navController: NavController) {
  Scaffold(
    topBar = {
      MainTopBar(
        title = "Games List",
        onActionClick = {
          navController.navigate(Search)
        }
      )
    }
  ) { innerPadding ->
    HomeViewContent(viewModel, innerPadding, navController)
  }
}


/**
 * Contenido de la vista principal que maneja la visualización de la lista de juegos.
 * Muestra un indicador de carga, la lista de juegos o un mensaje de error según el estado de los datos.
 * @param viewModel ViewModel que proporciona los datos de los juegos.
 * @param pad PaddingValues para manejar el espaciado adecuado dentro del Scaffold.
 * @param navController Controlador de navegación para manejar la navegación entre vistas.
 * @usage HomeViewContent(viewModel = gamesViewModel, pad = innerPadding, navController = navController)
 */
@Composable
fun HomeViewContent(viewModel: GamesViewModel, pad: PaddingValues, navController: NavController) {

  LaunchedEffect(Unit) {
    viewModel.fetchGames()
  }

  val gamesResource by viewModel.games.collectAsState()

  when (gamesResource) {
    is Resource.Loading -> {
      LoadingState()
    }
    is Resource.Success -> {
      val games = (gamesResource as Resource.Success<List<GameList>>).data
      if (games.isNotEmpty()) {
        LazyColumn(
          modifier = Modifier
            .padding(pad)
            .background(Color.Black)
        ) {
          items(games) { game ->
            CardGame(
              game = game,
              onClick = {
                navController.navigate(Detail(game.id))
              }
            )
          }
        }
      } else {
        NoContent(
          text = "No se encontraron juegos :( "
        )
      }
    }
    is Resource.Error -> {
      val errorMessage = (gamesResource as Resource.Error<List<GameList>>).message
      ErrorState(
        errorMessage = errorMessage
      )
    }
  }
}