package es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "tracks",
    foreignKeys = [
        ForeignKey(
            entity = Country::class,
            parentColumns = ["id"],
            childColumns = ["country_id"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class Track(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val country_id: Long,
    val distance: Double,
)
