package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.news.form.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.News
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Team
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.news.AddNewsUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.news.DeleteNewsUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.news.GetNewsByIdUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.news.GetNewsUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.news.UpdateNewsUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.news.form.view.NewsFormState
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.news.list.view.NewsState
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.team.view.TeamState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


open class NewsFormViewModel(
    private val addNewsUseCase: AddNewsUseCase,
    private val getNewsByIdUseCase: GetNewsByIdUseCase,
    private val updateNewsUseCase: UpdateNewsUseCase,
    private val deleteNewsUseCase: DeleteNewsUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel(

) {
    private val _state: MutableStateFlow<NewsFormState> = MutableStateFlow(NewsFormState.Success(""))
    val state: StateFlow<NewsFormState> = _state.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(), NewsFormState.Loading
    )

    private val _newsName = MutableStateFlow(savedStateHandle.get<String>("news_name") ?: "")
    val newsName: StateFlow<String> = _newsName

    private val _newsDescription = MutableStateFlow(savedStateHandle.get<String>("news_description") ?: "")
    val newsDescription: StateFlow<String> = _newsDescription

    private val newsId: String? = savedStateHandle.get<String?>("newsId")

    init {
        if (newsId != null) {
            if (_state.value != NewsFormState.Deleted) {
                loadNews(newsId)
            }
        }
    }

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

    fun loadNews(newsId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = getNewsByIdUseCase.getNewsById(newsId)
                if (result.isSuccess) {
                    val news = result.getOrNull()
                    news?.let {
                        _newsName.value = it.title
                        _newsDescription.value = it.description
                        _state.value = NewsFormState.NewsDetail(news)
                    }
                } else {
                    _state.value = NewsFormState.Error("Error loading news")
                }
            } catch (e: Exception) {
                _state.value = NewsFormState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun updateNews(newsId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val newsUpdated = News(
                    id = newsId,
                    title = _newsName.value,
                    description = _newsDescription.value
                )
                val result = updateNewsUseCase.updateNews(newsUpdated)
                if (!result.isSuccess) {
                    _state.value = NewsFormState.Error("Error updating news")
                }
            } catch (e: Exception) {
                _state.value = NewsFormState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun deleteNews(newsId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = deleteNewsUseCase.deleteNews(newsId)
                if (result.isSuccess) {
                    _state.value = NewsFormState.Deleted
                } else {
                    _state.value = NewsFormState.Error("Error deleting news")
                }
            } catch (e: Exception) {
                _state.value = NewsFormState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
