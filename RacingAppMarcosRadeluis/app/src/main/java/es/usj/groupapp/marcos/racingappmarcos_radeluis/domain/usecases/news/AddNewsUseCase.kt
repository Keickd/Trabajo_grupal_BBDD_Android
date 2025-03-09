package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.news

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.News
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository.NewsRepository

class AddNewsUseCase(private val repository: NewsRepository) {
    suspend fun addNews(news: News): Result<String> {
        return repository.addNews(news)
    }
}
