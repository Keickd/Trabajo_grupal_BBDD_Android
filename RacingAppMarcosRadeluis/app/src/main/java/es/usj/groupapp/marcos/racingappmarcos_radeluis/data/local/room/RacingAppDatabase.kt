package es.usj.groupapp.marcos.racingappmarcos_radeluis.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Country
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Participation
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Racer
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Race
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Track
import es.usj.groupapp.marcos.racingappmarcos_radeluis.domain.model.Team

@Database(entities = [ Country::class, Participation::class, Racer::class, Race::class, Track::class, Team::class], exportSchema = true, version = 1 )
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