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
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountry(countryEntity: CountryEntity)

    @Query("Select * FROM countries")
    fun getAllCountries(): Flow<List<CountryEntity>>

    @Query("Select * FROM countries where id = :id")
    suspend fun getCountryById(id: Long): CountryEntity

    @Update
    suspend fun updateCountry(countryEntity: CountryEntity)
}