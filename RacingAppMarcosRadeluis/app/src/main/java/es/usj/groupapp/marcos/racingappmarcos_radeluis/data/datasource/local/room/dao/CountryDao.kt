package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.entities.CountryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CountryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCountries(countries: List<CountryEntity>)

    @Query("Select * FROM countries")
    fun getAllCountries(): Flow<List<CountryEntity>>

    @Query("Select * FROM countries where id = :id")
    fun getCountryById(id: Long): Flow<CountryEntity>

    @Update
    suspend fun updateCountry(countryEntity: CountryEntity)
}