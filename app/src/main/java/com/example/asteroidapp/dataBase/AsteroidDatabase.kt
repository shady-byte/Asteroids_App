package com.example.asteroidapp.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AsteroidData::class], version = 3, exportSchema = false)
abstract class AsteroidDatabase: RoomDatabase() {
    abstract val databaseDao: AsteroidDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: AsteroidDatabase?=null

        fun getInstance(context: Context) : AsteroidDatabase {
            synchronized(lock = this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AsteroidDatabase::class.java, "asteroids_info_database")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}