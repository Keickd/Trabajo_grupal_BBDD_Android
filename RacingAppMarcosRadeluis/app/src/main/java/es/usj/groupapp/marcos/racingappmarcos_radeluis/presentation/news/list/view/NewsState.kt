package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.news.list.view

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.News

sealed class NewsState {
    object Loading : NewsState()
    data class Success(val news: List<News>) : NewsState()
    data class Error(val message: String) : NewsState()
}
