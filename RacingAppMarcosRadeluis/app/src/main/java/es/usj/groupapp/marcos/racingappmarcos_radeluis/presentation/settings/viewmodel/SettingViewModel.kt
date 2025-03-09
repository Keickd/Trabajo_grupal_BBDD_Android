package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.settings.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Setting
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.setting.GetSettingUseCase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.setting.SaveSettingUseCase
import kotlinx.coroutines.launch

class SettingViewModel(
    private val getSettingsUseCase: GetSettingUseCase,
    private val saveSettingsUseCase: SaveSettingUseCase
): ViewModel() {
    var isDarkMode: MutableState<Boolean> = mutableStateOf(false)

    init {
        getSettings()
    }

    fun toggleTheme() {
        viewModelScope.launch {
            isDarkMode.value = !isDarkMode.value
            this@SettingViewModel.saveSettings(Setting(isDarkMode.value))
        }
    }

    fun getSettings(){
        viewModelScope.launch {
            getSettingsUseCase.getSettings().collect{
                isDarkMode.value = it.darkMode
            }
        }
    }

    suspend fun saveSettings(setting: Setting){
        saveSettingsUseCase.saveSetting(setting)
    }
}