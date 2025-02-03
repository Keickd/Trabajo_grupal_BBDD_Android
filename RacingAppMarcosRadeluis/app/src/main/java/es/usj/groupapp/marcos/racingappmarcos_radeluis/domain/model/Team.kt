package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "teams")
data class Team(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String
)
