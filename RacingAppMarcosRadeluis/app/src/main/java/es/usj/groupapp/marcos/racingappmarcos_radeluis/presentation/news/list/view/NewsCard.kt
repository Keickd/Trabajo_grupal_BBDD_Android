package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.news.list.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.News

@Composable
fun NewsCard(
    news: News,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Card(
        modifier = modifier.clickable {
            navController.navigate("news_form/${news.id}")
        },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = news.title, style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = news.description, style = MaterialTheme.typography.titleLarge)
        }
    }
}
