package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Winner(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val race_id : Long,
    val racer_id: Long,
)
