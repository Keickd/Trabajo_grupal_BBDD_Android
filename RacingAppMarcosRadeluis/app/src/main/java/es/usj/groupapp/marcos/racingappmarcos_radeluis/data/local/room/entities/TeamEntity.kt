package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "team",
foreignKeys = [
    ForeignKey(
    entity = CountryEntity::class,
    parentColumns = ["id"],
    childColumns = ["country_id"],
    onDelete = ForeignKey.SET_NULL
    )]
)
data class TeamEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val country_id: Long
)
