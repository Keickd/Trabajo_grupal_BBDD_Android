package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.news

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.News
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository.NewsRepository

class UpdateNewsUseCase(private val newsRepository: NewsRepository) {

    suspend fun updateNews(news: News): Result<String> {
        return newsRepository.updateNews(news)
    }
}
