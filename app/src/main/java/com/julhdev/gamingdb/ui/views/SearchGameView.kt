package com.julhdev.gamingdb.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.julhdev.gamingdb.data.model.GameList
import com.julhdev.gamingdb.navigation.Detail
import com.julhdev.gamingdb.ui.components.CardGame
import com.julhdev.gamingdb.util.resource.Resource
import com.julhdev.gamingdb.viewmodels.GamesViewModel


/**
 * Composable que representa la vista de búsqueda de juegos.
 * Permite al usuario buscar juegos y muestra los resultados en una lista.
 * @param viewModel El ViewModel asociado a la vista.
 * @param navController El controlador de navegación de la aplicación.
 * @usage SearchGameView(viewModel = gamesViewModel, navController = navController)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchGameView(viewModel: GamesViewModel, navController: NavController) {

  val gamesResource by viewModel.games.collectAsState()
  var expanded by rememberSaveable { mutableStateOf(false) }
  var query by remember { mutableStateOf("") }

  DisposableEffect(Unit) {
    onDispose {
      viewModel.cleanState()
    }
  }

  Scaffold(
    modifier = Modifier
      .background(Color.Black)
  ) { innerPadding ->
    SearchBar(
      modifier = Modifier
        .semantics { traversalIndex = 0f }
        .fillMaxWidth()
        .padding(innerPadding)
        .padding(horizontal = 10.dp),
      inputField = {
        SearchBarDefaults.InputField(
          query = query,
          onQueryChange = { newQuery -> query = newQuery },
          onSearch = {
            viewModel.fetchGames(query)
          },
          expanded = expanded,
          onExpandedChange = { expanded = it },
          placeholder = { Text("Search games...") },
          leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
          trailingIcon = {
            Icon(
              Icons.Default.Cancel,
              contentDescription = "Back Icon",
              modifier = Modifier.clickable {
                navController.popBackStack()
              }
            )
          }
        )
      },
      expanded = expanded,
      onExpandedChange = { expanded = it },
      content = {
        LazyColumn {
          if (gamesResource is Resource.Success) {
            val games = (gamesResource as Resource.Success<List<GameList>>).data
            items(games) { game ->
              Box(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(8.dp)
                  .semantics { traversalIndex = games.indexOf(game).toFloat() }
              ) {
                CardGame(
                  game = game,
                  onClick = {
                    navController.navigate(Detail(game.id))
                  }
                )
              }
            }
          } else {
            item {
              if (gamesResource is Resource.Loading) {
                Text(
                  text = "Loading...",
                  modifier = Modifier.padding(16.dp)
                )
              } else if (gamesResource is Resource.Error) {
                val errorMessage = (gamesResource as Resource.Error).message
                Text(
                  text = errorMessage ?: "An unknown error occurred",
                  modifier = Modifier.padding(16.dp)
                )
              }
            }
          }
        }
      }
    )
  }
}
