package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.News
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNews(): Flow<List<News>>
    suspend fun getNewsById(newsId: String): Result<News>
    suspend fun addNews(news: News): Result<String>
    suspend fun updateNews(news: News): Result<String>
    suspend fun deleteNews(newsId: String): Result<String>
}
