package com.julhdev.gamingdb.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.julhdev.gamingdb.ui.views.DetailsView
import com.julhdev.gamingdb.ui.views.HomeView
import com.julhdev.gamingdb.viewmodels.GamesViewModel
import com.julhdev.gamingdb.ui.views.SearchGameView


@Composable
fun NavController(
    gamesViewModel: GamesViewModel
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Home
    ){
        composable<Home> {
            HomeView(
                viewModel = gamesViewModel,
                navController = navController
            )
        }

        composable<Search> {
            SearchGameView(
                viewModel = gamesViewModel,
                navController = navController
            )
        }

        composable<Detail> { backstackEntry ->
            val id = backstackEntry.toRoute<Detail>()
            DetailsView(
                viewmodel = gamesViewModel,
                navController = navController,
                gameId = id.id
            )
        }
    }

}