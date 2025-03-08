package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.repository

import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.remote.datasources.NewsDataSource
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.News
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class NewsRepositoryImpl(private val newsDataSource: NewsDataSource) : NewsRepository {

    override suspend fun getNews(): Flow<List<News>> {
        return newsDataSource.getNews()
    }
}
