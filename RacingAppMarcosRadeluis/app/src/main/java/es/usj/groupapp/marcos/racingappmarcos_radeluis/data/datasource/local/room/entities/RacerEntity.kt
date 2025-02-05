package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.room.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Index
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.entities.CountryEntity

@Entity(
    tableName = "racers",
    foreignKeys = [
        ForeignKey(
            entity = TeamEntity::class,
            parentColumns = ["id"],
            childColumns = ["team_id"],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = CountryEntity::class,
            parentColumns = ["id"],
            childColumns = ["country_id"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index(value = ["team_id"]),
        Index(value = ["country_id"])
    ]
)
data class RacerEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val country_id: Long,
    val age: Int,
    val team_id: Long
)
