package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Setting
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingDataStoreDataSource(private val dataStore: DataStore<Preferences>) {
    companion object {
        private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
    }

    suspend fun saveSetting(setting: Setting) {
        dataStore.edit { preferences ->
            preferences[DARK_MODE_KEY] = setting.darkMode
        }
    }

    fun getSetting(): Flow<Setting>{
        return dataStore.data.map { preferences ->
            Setting(preferences[DARK_MODE_KEY] ?: false)
        }

    }
}