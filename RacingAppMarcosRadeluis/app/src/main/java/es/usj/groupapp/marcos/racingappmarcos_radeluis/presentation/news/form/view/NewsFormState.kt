package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.news.form.view

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.News

sealed class NewsFormState {
    data class Success(val msg: String) : NewsFormState()
    data object Loading: NewsFormState()
    data class Error(val message: String) : NewsFormState()
    data class NewsDetail(val news: News): NewsFormState()
    data object Deleted: NewsFormState()
}