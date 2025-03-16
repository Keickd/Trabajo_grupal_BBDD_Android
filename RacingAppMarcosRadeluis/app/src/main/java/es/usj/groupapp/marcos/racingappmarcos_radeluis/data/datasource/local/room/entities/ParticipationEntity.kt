package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.room.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(
    tableName = "participations",
    foreignKeys = [
        ForeignKey(
            entity = RacerEntity::class,
            parentColumns = ["id"],
            childColumns = ["racer_id"],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = RaceEntity::class,
            parentColumns = ["id"],
            childColumns = ["race_id"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index(value = ["racer_id"]),
        Index(value = ["race_id"])
    ]
)
data class ParticipationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val race_id: Long,
    val racer_id: Long,
    val ranking: Int?,
    val best_time: Long?
)
