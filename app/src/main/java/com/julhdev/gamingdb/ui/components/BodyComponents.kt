package com.julhdev.gamingdb.ui.components

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.julhdev.gamingdb.data.model.GameList
import com.julhdev.gamingdb.ui.theme.CustomBlack
import androidx.core.net.toUri

/**
 * Top Bar Principal de la App
 * @param title Título a mostrar
 * @param showBackBtn Indica si se muestra el botón de retroceso
 * @param onBackClick Acción al hacer clic en el botón de retroceso
 * @usage MainTopBar(title = "Título", showBackBtn = true, onBackClick = { /* Acción */ })
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
  title: String,
  showBackBtn: Boolean = false,
  onBackClick: () -> Unit = { },
  onActionClick: () -> Unit = { }
) {
  TopAppBar(
    title = {
      Text(
        text = title,
        color = Color.White,
        fontWeight = FontWeight.ExtraBold
      )
    },
    colors = TopAppBarDefaults.topAppBarColors(
      containerColor = CustomBlack
    ),
    navigationIcon = {
      if (showBackBtn) {
        IconButton(
          onClick = { onBackClick() }
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back Button",
            tint = Color.White
          )
        }
      }
    },
    actions = {
      if (!showBackBtn) {
        IconButton(
          onClick = { onActionClick() }
        ) {
          Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search Button",
            tint = Color.White
          )
        }
      }
    }
  )
}

/**
 * Componente de tarjeta para mostrar información de un juego.
 * @param game Objeto GameList que contiene la información del juego.
 * @param onClick Función lambda que se ejecuta al hacer clic en la tarjeta.
 * @usage CardGame(game = gameObject, onClick = { /* Acción al hacer clic */ })
 */
@Composable
fun CardGame(
  game: GameList,
  onClick: () -> Unit
) {
  Card(
    shape = RoundedCornerShape(5.dp),
    colors = CardDefaults.cardColors(
      containerColor = CustomBlack,
      contentColor = Color.White
    ),
    modifier = Modifier
      .padding(10.dp)
      .shadow(40.dp)
      .clickable { onClick() }
  ) {
    Column()
    {
      MainImage(image = game.backgroundImage)
      Text(
        text = game.name,
        fontWeight = FontWeight.ExtraBold,
        modifier = Modifier
          .padding(15.dp)
      )
    }
  }
}

/**
 * Componente para mostrar la imagen principal de un juego.
 * @param image URL de la imagen a mostrar.
 * @usage MainImage(image = "https://example.com/image.jpg")
 */
@Composable
fun MainImage(
  image: String
) {
  val image = rememberAsyncImagePainter(model = image)
  Image(
    painter = image,
    contentDescription = "Game Image",
    contentScale = ContentScale.Crop,
    modifier = Modifier
      .fillMaxWidth()
      .height(250.dp)
  )
}

/**
 * Componente de tarjeta para mostrar la puntuación de Metacritic.
 * @param score Puntuación de Metacritic del juego.
 * @usage MetacriticCard(score = 85)
 */
@Composable
fun MetacriticCard(score: Int) {

  val cardColor = when (score) {
    in 75..100 -> Color.Green
    in 50..74 -> Color.Yellow
    else -> Color.Red
  }

  Card(
    shape = RoundedCornerShape(10.dp),
    colors = CardDefaults.cardColors(
      containerColor = cardColor
    ),
    modifier = Modifier
      .padding(16.dp)
  ) {
    Column(
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
        .padding(16.dp)
    ) {
      Text(
        text = "$score",
        fontWeight = FontWeight.ExtraBold,
        color = Color.White,
        fontSize = 50.sp
      )
    }
  }
}

@Composable
fun MetacriticWebSite(
  url: String
) {
  val context = LocalContext.current
  val intent = Intent(Intent.ACTION_VIEW, url.toUri())

  Column {
    Text(
      text = "METASCORE",
      color = Color.White,
      fontWeight = FontWeight.Bold,
      fontSize = 30.sp,
      modifier = Modifier
        .padding(vertical = 10.dp)
    )

    Button(
      onClick = { context.startActivity(intent) },
      colors = ButtonDefaults.buttonColors(
        containerColor = CustomBlack,
        contentColor = Color.White
      )
    ) {
      Text(
        text = "Sitio Web"
      )
    }
  }

}