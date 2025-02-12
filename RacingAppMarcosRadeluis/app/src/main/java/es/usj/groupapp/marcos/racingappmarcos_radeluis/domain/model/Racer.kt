package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model

data class Racer(
    val id: Long = 0,
    val name: String,
    val image: String,
    val country: Country,
    val age: Int,
    val team: Team
)