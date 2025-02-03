package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.entities.CountryEntity
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.entities.ParticipationEntity
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.entities.RacerEntity
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.entities.RaceEntity
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.entities.TrackEntity
import es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room.entities.TeamEntity

@Database(entities = [ CountryEntity::class, ParticipationEntity::class, RacerEntity::class, RaceEntity::class, TrackEntity::class, TeamEntity::class], exportSchema = true, version = 1 )
abstract class RacingAppDatabase : RoomDatabase() {

//    abstract fun getDao(): MonumentDao

    companion object {

        var db: RacingAppDatabase? = null

        fun provideDatabase(application: Context): RacingAppDatabase {
            if (db == null) {
                db = Room.databaseBuilder(
                    application,
                    RacingAppDatabase::class.java, "RacingAppDb.db"
                ).build()
            }
            return db!!
        }
    }
}