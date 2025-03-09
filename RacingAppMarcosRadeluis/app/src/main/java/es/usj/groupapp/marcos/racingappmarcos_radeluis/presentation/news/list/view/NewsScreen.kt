package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.news.list.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.view.FailureComposable
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.view.HomeState
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.home.view.LoadingComposable
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.news.list.viewmodel.NewsViewModel

@Composable
fun NewsScreen(
    newsViewModel: NewsViewModel,  onAddNewsClick: () -> Unit, navController: NavController,
) {
    val newsState = newsViewModel.newsStateFlow.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "NEWS",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold
            )

            IconButton(onClick = onAddNewsClick ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar noticia",
                    tint = Color.Black,
                    modifier = Modifier.size(40.dp)
                )
            }
        }

        when (val state = newsState.value) {
            is NewsState.Loading -> LoadingComposable()
            is NewsState.Success -> {
                state.news.forEach { individualNews ->
                    NewsCard(
                        title = individualNews.title,
                        description = individualNews.description,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 10.dp),
                    )
                }
            }
            is NewsState.Error -> FailureComposable()
        }
    }
}
