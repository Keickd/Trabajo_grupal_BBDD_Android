package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.repository

import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.datastore.SettingDataStoreDataSource
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Setting
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.repository.SettingRepository
import kotlinx.coroutines.flow.Flow

class SettingRepositoryImpl(private val dataSource: SettingDataStoreDataSource): SettingRepository  {
    override suspend fun saveSetting(setting: Setting) {
        return dataSource.saveSetting(setting)
    }

    override fun getSetting(): Flow<Setting> {
        return dataSource.getSetting()
    }
}