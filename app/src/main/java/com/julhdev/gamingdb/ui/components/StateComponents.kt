package com.julhdev.gamingdb.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * Composable que representa o estado de carregamento.
 * @usage LoadingState()
 */
@Composable
fun LoadingState(
) {
  Box(
    modifier = Modifier
      .fillMaxSize(),
    contentAlignment = Alignment.Center
  ) {
    CircularProgressIndicator()
  }
}

/**
 * Composable que representa o estado de conteúdo vazio.
 * @param text Texto a ser exibido no layout.
 * @usage NoContent(PaddingValues(16.dp), "No content")
 */
@Composable
fun NoContent(
  text: String
) {
  Box(
    modifier = Modifier
      .fillMaxSize(),
    contentAlignment = Alignment.Center
  ) {
    Text(text = text)
  }
}

/**
 * Composable que representa o estado de erro.
 * @param errorMessage Mensagem de erro a ser exibida.
 * @usage ErrorState(PaddingValues(16.dp), "Error message")
 */
@Composable
fun ErrorState(
  errorMessage: String
) {
  Box(
    modifier = Modifier
      .fillMaxSize(),
    contentAlignment = Alignment.Center
  ) {
    Text(text = errorMessage)
  }
}