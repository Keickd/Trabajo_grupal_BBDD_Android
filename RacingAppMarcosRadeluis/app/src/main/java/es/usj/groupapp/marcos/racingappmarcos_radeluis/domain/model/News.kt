package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model

data class News(
    val title: String = "",
    val description: String = "",
) {
    constructor() : this("", "")
}
