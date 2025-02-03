package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model

import java.util.Date

data class Race(
    val id: Long = 0,
    val date: Date,
    val track_id: Long
)
