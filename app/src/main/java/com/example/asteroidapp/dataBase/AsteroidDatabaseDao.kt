package com.example.asteroidapp.dataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy

@Dao
interface AsteroidDatabaseDao {
//to insert all asteroids in database after fetching from network
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAsteroids(vararg asteroids: AsteroidData)

//to get all asteroids from database
    @Query("SELECT * FROM asteroids_data_table WHERE close_approach_date_column >= :startDate ORDER BY close_approach_date_column ASC")
    suspend fun getAllAsteroids(startDate: String): List<AsteroidData>

//to get all asteroids of today
    @Query("SELECT * FROM asteroids_data_table WHERE close_approach_date_column == :todayDate")
    suspend fun getNewAsteroids(todayDate: String): List<AsteroidData>

//to delete all asteroids from database
    @Query("DELETE FROM asteroids_data_table")
    suspend fun clearDatabase()
}