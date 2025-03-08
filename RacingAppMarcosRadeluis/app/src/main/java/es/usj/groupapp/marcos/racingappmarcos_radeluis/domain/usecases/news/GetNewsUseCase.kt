package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.news

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.News
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetNewsUseCase(private val repository: NewsRepository) {
    suspend fun getNews(): Flow<List<News>> = repository.getNews()
}
