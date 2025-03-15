package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.mappers.DateMapper
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.mappers.TrackMapper
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.dao.CountryDao
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.entities.CountryEntity
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.room.entities.ParticipationEntity
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.room.entities.RacerEntity
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.room.entities.RaceEntity
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.room.entities.TrackEntity
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.datasource.local.room.entities.TeamEntity
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.dao.RaceDao
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.dao.RacerDao
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.dao.TeamDao
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.dao.TrackDao

@Database(entities = [ CountryEntity::class, ParticipationEntity::class, RacerEntity::class, RaceEntity::class, TrackEntity::class, TeamEntity::class], exportSchema = false, version = 1 )
@TypeConverters(DateMapper::class)
abstract class RacingAppDatabase : RoomDatabase() {

    abstract fun countryDao(): CountryDao
    abstract fun teamDao(): TeamDao
    abstract fun racerDao(): RacerDao
    abstract fun trackDao(): TrackDao
    abstract fun raceDao(): RaceDao


    companion object {
        @Volatile
        private var INSTANCE: RacingAppDatabase? = null

        fun provideDatabase(application: Context): RacingAppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    application,
                    RacingAppDatabase::class.java, "RacingAppDb.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}