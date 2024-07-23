/** A class that initializes the RoutineDatabase,
 * A Database entity implemented with the androidx.room.Room library,
 * a commonly used library for implementing local databases in
 * Android and Jetpack Compose. Uses SQLite for implementation.
 *
 * Uses the Routine database object, 'routineDao' from RoutineDao.kt
 * to define the set of SQL queries that can be used
 * to interact with the 'Routine' and 'Exercise' entities.
 *
 * */


package com.example.bottomnavigationpractice.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [Routine::class,Exercise::class,ProgressItem::class,ProgressDataPoint::class],
    version = 7,
    exportSchema = false
)
abstract class RoutineDatabase:RoomDatabase(){
    abstract fun routineDao(): RoutineDao
    abstract fun progressItemDao(): ProgressItemDao

    companion object{
        @Volatile

        var Instance: RoutineDatabase?= null
        fun getDatabase(context: Context): RoutineDatabase{
            return Instance ?: synchronized(this){
                Room.databaseBuilder(
                    context,
                    RoutineDatabase::class.java,
                    "routine_db"
                )  .fallbackToDestructiveMigration()
                    .build()
                    .also{Instance = it}

            }
        }
    }
}
