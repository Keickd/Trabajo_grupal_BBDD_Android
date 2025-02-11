package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.datasources

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.mappers.CountryMapper
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.dao.CountryDao
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Country
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import java.io.InputStreamReader

class CountryLocalDataSource(
    private val context: Context,
    private val countryDao: CountryDao,
    private val countryMapper: CountryMapper
) {

    suspend fun insertAndGetCountriesFromJSON(): List<Country> {
        val existingCountries = withContext(Dispatchers.IO) {
            countryDao.getAllCountries().firstOrNull()
        }

        if (!existingCountries.isNullOrEmpty()) {
            Log.d("DEBUG", "Los países ya están en la base de datos, no se vuelven a insertar.")
            return existingCountries.map { countryMapper.mapToDomain(it) }
        }

        Log.d("DEBUG", "Base de datos vacía, cargando datos desde JSON.")

        val countriesJson = context.assets.open("countries.json").use { inputStream ->
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