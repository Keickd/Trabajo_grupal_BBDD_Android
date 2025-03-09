package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.news.form.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.News
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.news.AddNewsUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.news.form.view.NewsFormState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


open class NewsFormViewModel(private val addNewsUseCase: AddNewsUseCase, savedStateHandle: SavedStateHandle
): ViewModel(

) {
    private val _state: MutableStateFlow<NewsFormState> = MutableStateFlow(NewsFormState.Success(""))
    val state: StateFlow<NewsFormState> = _state.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(), NewsFormState.Success("")
    )

    private val _newsName = MutableStateFlow(savedStateHandle.get<String>("news_name") ?: "")
    val newsName: StateFlow<String> = _newsName

    private val _newsDescription = MutableStateFlow(savedStateHandle.get<String>("news_description") ?: "")
    val newsDescription: StateFlow<String> = _newsDescription

    fun updateNewsName(newName: String) {
        _newsName.value = newName
    }

    fun updateNewsDescription(newName: String) {
        _newsDescription.value = newName
    }

    fun addNews() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val news = News(
                    title = newsName.value,
                    description = newsDescription.value
                )
                addNewsUseCase.addNews(news)
            } catch (e: Exception) {
                _state.value = NewsFormState.Error(e.message ?: "Unknown error")
            }
        }
    }

}
