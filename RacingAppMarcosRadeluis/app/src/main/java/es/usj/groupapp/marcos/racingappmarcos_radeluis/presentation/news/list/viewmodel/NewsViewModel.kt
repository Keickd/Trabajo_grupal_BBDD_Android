package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.news.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.news.GetNewsUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.news.list.view.NewsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


open class NewsViewModel(private val getNewsUseCase: GetNewsUseCase,
): ViewModel() {
    private val _newsStateFlow = MutableStateFlow<NewsState>(NewsState.Loading)
    val newsStateFlow: StateFlow<NewsState> = _newsStateFlow

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            getNewsUseCase.getNews().collect { news ->
                _newsStateFlow.value = if (news.isNotEmpty()) {
                    NewsState.Success(news)
                } else {
                    NewsState.Error("No se encontraron noticias")
                }
            }
        }
    }
}
