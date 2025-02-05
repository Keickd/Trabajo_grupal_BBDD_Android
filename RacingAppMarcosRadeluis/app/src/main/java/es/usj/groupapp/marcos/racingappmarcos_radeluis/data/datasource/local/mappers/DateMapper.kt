package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.mappers

import androidx.room.TypeConverter
import java.util.Date

class DateMapper {
    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }
}
