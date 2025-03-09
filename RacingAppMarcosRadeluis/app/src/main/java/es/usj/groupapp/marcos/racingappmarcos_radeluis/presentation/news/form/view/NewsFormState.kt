package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.news.form.view

sealed class NewsFormState {
    data class Success(val msg: String) : NewsFormState()
    data class Error(val message: String) : NewsFormState()
}