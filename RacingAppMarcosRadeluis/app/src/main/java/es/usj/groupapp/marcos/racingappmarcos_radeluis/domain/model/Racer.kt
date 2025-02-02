package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Team::class,
        parentColumns = ["id"],
        childColumns = ["team_id"],
        onDelete = ForeignKey.SET_NULL
    )]
)
data class Racer(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val nationality: String,
    val age: Int,
    val team_id: Long
)
