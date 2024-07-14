package com.example.bottomnavigationpractice.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [Routine::class,Exercise::class],
    version = 2,
    exportSchema = false
)
abstract class RoutineDatabase:RoomDatabase(){
    abstract fun routineDao(): RoutineDao

    companion object{
        @Volatile

        var Instance: RoutineDatabase?= null
        fun getDatabase(context: Context): RoutineDatabase{
            return Instance ?: synchronized(this){
                Room.databaseBuilder(
                    context,
                    RoutineDatabase::class.java,
                    "routine_db"
                ).build().also{Instance = it}

            }
        }
    }
}
