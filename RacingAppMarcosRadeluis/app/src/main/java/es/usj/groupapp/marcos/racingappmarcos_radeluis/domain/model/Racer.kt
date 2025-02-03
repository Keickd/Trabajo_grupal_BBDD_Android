package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model

data class Racer(
    val id: Long = 0,
    val name: String,
    val country_id: Long,
    val age: Int,
    val team_id: Long
)