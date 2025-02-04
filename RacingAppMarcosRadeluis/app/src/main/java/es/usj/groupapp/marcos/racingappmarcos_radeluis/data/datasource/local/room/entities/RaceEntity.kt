package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "races",
    foreignKeys = [
        ForeignKey(
            entity = TrackEntity::class,
            parentColumns = ["id"],
            childColumns = ["track_id"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class RaceEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: Date,
    val track_id: Long
)
