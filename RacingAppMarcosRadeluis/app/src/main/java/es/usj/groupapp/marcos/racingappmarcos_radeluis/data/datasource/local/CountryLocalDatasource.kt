package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local

import android.content.Context
import com.google.gson.Gson
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.mapper.CountryMapper
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.dao.CountryDao
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Country
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStreamReader

class CountryLocalDataSource(
    private val context: Context,
    private val countryDao: CountryDao,
    private val countryMapper: CountryMapper
) {

    suspend fun insertAndGetCountriesFromJSON(): List<Country> {
        val countriesJson = context.assets.open("flags/countries.json").use { inputStream ->
            InputStreamReader(inputStream).readText()
        }

        val countryList = Gson().fromJson(countriesJson, Array<Country>::class.java).toList()
        val countryEntities = countryList.map { countryMapper.mapToEntity(it) }

        withContext(Dispatchers.IO) {
            countryDao.insertCountries(countryEntities)
        }

        return countryList
    }
}