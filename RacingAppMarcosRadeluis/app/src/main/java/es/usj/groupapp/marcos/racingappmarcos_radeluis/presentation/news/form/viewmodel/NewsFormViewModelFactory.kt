package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.news.form.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.remote.datasources.NewsDataSource
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.repository.NewsRepositoryImpl
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.news.AddNewsUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.news.GetNewsUseCase


class NewsFormViewModelFactory(
    db: FirebaseFirestore,
    private val savedStateHandle: SavedStateHandle,
) : ViewModelProvider.Factory {

    //Datasources
    val newsDatasource = NewsDataSource(db)

    //Impl
    private val newsRepositoryImpl = NewsRepositoryImpl(newsDatasource)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsFormViewModel(
            AddNewsUseCase(newsRepositoryImpl),
            savedStateHandle = savedStateHandle,
        ) as T
    }
}
