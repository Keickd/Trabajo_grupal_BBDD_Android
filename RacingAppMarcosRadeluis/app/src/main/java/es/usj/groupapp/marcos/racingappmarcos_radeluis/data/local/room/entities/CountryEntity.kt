package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country")
data class CountryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String
)
