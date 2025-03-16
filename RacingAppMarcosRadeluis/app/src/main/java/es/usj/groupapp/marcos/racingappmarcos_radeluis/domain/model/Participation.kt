package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model

data class Participation(
    val id: Long = 0,
    val race_id: Long,
    val racer_id: Long,
    val ranking: Int?,
    val best_time: Long?,
    val racer: Racer? = null
)
