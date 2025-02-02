package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Racer::class,
            parentColumns = ["id"],
            childColumns = ["racer_id"],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = Race::class,
            parentColumns = ["id"],
            childColumns = ["race_id"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class Participation(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val track_id: Long,
    val racer_id: Long,
    val ranking: Int,
    val best_time: Long
)
