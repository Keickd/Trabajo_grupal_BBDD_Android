package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.usecases.setting

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Setting
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository.SettingRepository
import kotlinx.coroutines.flow.Flow

class GetSettingUseCase(private val settingRepository: SettingRepository) {
    fun getSettings(): Flow<Setting> {
        return settingRepository.getSetting()
    }
}