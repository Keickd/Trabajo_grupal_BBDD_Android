package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.news.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.remote.datasources.NewsDataSource
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.repository.NewsRepositoryImpl
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.news.GetNewsUseCase


class NewsViewModelFactory() : ViewModelProvider.Factory {

    //Datasources
    val newsDatasource = NewsDataSource(FirebaseFirestore.getInstance())

    //Impl
    private val newsRepositoryImpl = NewsRepositoryImpl(newsDatasource)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(
            GetNewsUseCase(newsRepositoryImpl)
        ) as T
    }
}
