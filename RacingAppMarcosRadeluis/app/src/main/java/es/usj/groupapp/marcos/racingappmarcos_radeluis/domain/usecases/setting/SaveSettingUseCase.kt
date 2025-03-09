package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.setting

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Setting
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository.SettingRepository

class SaveSettingUseCase(val repository: SettingRepository) {
    suspend fun saveSetting(setting: Setting) {
        repository.saveSetting(setting)
    }
}