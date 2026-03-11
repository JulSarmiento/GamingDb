package com.julhdev.gamingdb.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.julhdev.gamingdb.data.model.SingleGameModel
import com.julhdev.gamingdb.ui.components.ErrorState
import com.julhdev.gamingdb.ui.components.LoadingState
import com.julhdev.gamingdb.ui.components.MainImage
import com.julhdev.gamingdb.ui.components.MainTopBar
import com.julhdev.gamingdb.ui.components.MetacriticCard
import com.julhdev.gamingdb.ui.components.MetacriticWebSite
import com.julhdev.gamingdb.util.resource.Resource
import com.julhdev.gamingdb.viewmodels.GamesViewModel


/**
 * Composable que representa la vista de detalles de un juego.
 * @param viewModel El ViewModel asociado a la vista.
 * @param navController El controlador de navegación de la aplicación.
 * @param gameId El ID del juego para el cual se mostrarán los detalles.
 * @usage DetailsView(viewmodel, navController, gameId)
 */
@Composable
fun DetailsView(viewModel: GamesViewModel, navController: NavController, gameId: Int) {
  val gameResource by viewModel.state.collectAsState(initial = Resource.Loading())

  LaunchedEffect(key1 = gameId) {
    viewModel.getGameById(gameId)
  }

  DisposableEffect(Unit) {
    onDispose {
      viewModel.cleanState()
    }
  }

  Scaffold(
    topBar = {
      MainTopBar(
        title = if (gameResource is Resource.Success) (gameResource as Resource.Success<SingleGameModel>).data.name else "Detalles del juego",
        showBackBtn = true,
        onBackClick = { navController.popBackStack() }
      )
    }
  ) { innerPadding ->
    Column(
      modifier = Modifier
        .padding(innerPadding)
        .background(Color.Black)
    ) {
      when (val res = gameResource) {
        is Resource.Loading -> {
          LoadingState()
        }

        is Resource.Success -> {
          val gameDetails = res.data
          DetailsViewContent(gameDetails)
        }

        is Resource.Error -> {
          val errorMessage = res.message
          ErrorState(
            errorMessage = errorMessage
          )
        }
      }
    }
  }
}


/**
 * Composable que muestra el contenido de los detalles del juego.
 * @param gameDetails Objeto SingleGameModel que contiene los detalles del juego.
 * @usage DetailsViewContent(gameDetails)
 */
@Composable
fun DetailsViewContent(gameDetails: SingleGameModel) {
  val scroll = rememberScrollState(0)

  MainImage(
    image = gameDetails.backgroundImage,
  )
  Spacer(
    modifier = Modifier
      .height(10.dp)
  )
  Row(
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
      .fillMaxWidth()
      .padding(start = 20.dp, end = 5.dp)
  ) {
    MetacriticWebSite(
      url = gameDetails.website,
    )
    MetacriticCard(
      score = gameDetails.metacritic,
    )
  }

  Text(
    text = gameDetails.description,
    textAlign = TextAlign.Justify,
    color = Color.White,
    modifier = Modifier
      .padding(start = 15.dp, end = 15.dp, top = 10.dp,  bottom = 10.dp)
      .fillMaxWidth()
      .verticalScroll(
        scroll
      )
  )
}

