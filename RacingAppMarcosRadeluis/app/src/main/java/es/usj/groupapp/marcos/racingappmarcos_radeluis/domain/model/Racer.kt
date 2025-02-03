package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "racers",
    foreignKeys = [
        ForeignKey(
        entity = Team::class,
        parentColumns = ["id"],
        childColumns = ["team_id"],
        onDelete = ForeignKey.SET_NULL
         ),
        ForeignKey(
            entity = Country::class,
            parentColumns = ["id"],
            childColumns = ["country_id"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class Racer(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val country_id: Long,
    val age: Int,
    val team_id: Long
)
