package com.julhdev.gamingdb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.julhdev.gamingdb.navigation.NavController
import com.julhdev.gamingdb.ui.theme.GamingDbTheme
import com.julhdev.gamingdb.viewmodels.GamesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    val viewModel: GamesViewModel by viewModels()
    setContent {
      GamingDbTheme {
        NavController(viewModel)
      }
    }
  }
}



