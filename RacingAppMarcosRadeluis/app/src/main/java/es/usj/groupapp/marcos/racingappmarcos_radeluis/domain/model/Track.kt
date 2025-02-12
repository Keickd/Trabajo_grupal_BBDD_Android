package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model

data class Track(
    val id: Long = 0,
    val name: String,
    val image: String,
    val country: Country,
    val distance: Double,
)