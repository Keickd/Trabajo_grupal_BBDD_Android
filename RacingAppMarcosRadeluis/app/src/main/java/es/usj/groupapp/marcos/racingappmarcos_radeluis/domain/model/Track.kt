package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model

data class Track(
    val id: Long = 0,
    val name: String,
    val country_id: Long,
    val distance: Double,
)