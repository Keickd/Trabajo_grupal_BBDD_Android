package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.settings.viewmodel

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.datastore.SettingDataStoreDataSource
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.repository.SettingRepositoryImpl
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.setting.GetSettingUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.setting.SaveSettingUseCase

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")

class SettingViewModelFactory(
    context: Context
): ViewModelProvider.Factory {
    val dataStore = context.dataStore
    private val datasource = SettingDataStoreDataSource(dataStore)
    private val repository = SettingRepositoryImpl(datasource)


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(
                GetSettingUseCase(repository),
                SaveSettingUseCase(repository)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}