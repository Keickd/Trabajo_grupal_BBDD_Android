package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository

import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Setting
import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    suspend fun saveSetting(setting: Setting)
    fun getSetting(): Flow<Setting>
}