package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.news

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.News
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository.NewsRepository

class GetNewsByIdUseCase(private val repository: NewsRepository) {
    suspend fun getNewsById(newsId: String): Result<News> = repository.getNewsById(newsId)
}
