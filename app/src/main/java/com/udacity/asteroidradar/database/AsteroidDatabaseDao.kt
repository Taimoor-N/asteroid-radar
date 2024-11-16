package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface AsteroidDatabaseDao {

    @Insert
    suspend fun insert(asteroidEntity: AsteroidEntity)

    @Update
    suspend fun update(asteroidEntity: AsteroidEntity)

    @Query("SELECT * FROM asteroid_table WHERE asteroidId = :key")
    suspend fun get(key: Long): AsteroidEntity?

    // Return all asteroids ordered by the close approach date
    @Query("SELECT * FROM asteroid_table ORDER BY close_approach_date")
    suspend fun getAllAsteroids(): LiveData<List<AsteroidEntity>>

    // Delete all values from the table
    @Query("DELETE FROM asteroid_table")
    suspend fun clear()

}