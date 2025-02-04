package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "racers",
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
    ]
)
data class RacerEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val country_id: Long,
    val age: Int,
    val team_id: Long
)
