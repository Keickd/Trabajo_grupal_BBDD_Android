package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.news

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository.NewsRepository

class DeleteNewsUseCase(private val newsRepository: NewsRepository) {
    suspend  fun deleteNews(newsId: String): Result<String> {
        return newsRepository.deleteNews(newsId)
    }
}
