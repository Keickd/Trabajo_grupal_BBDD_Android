package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "races",
    foreignKeys = [
        ForeignKey(
            entity = Track::class,
            parentColumns = ["id"],
            childColumns = ["track_id"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class Race(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: Date,
    val track_id: Long
)
