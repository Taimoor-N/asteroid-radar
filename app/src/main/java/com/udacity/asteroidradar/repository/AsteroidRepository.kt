package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.asDatabaseModel
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AsteroidRepository(private val database: AsteroidDatabase) {

    val asteroids: LiveData<List<Asteroid>> = database.asteroidDataDao.getAllAsteroids()
        .map { it.asDomainModel() }

    suspend fun addAsteroid(asteroid: Asteroid) {
        withContext(Dispatchers.IO) {
            database.asteroidDataDao.insert(asteroid.asDatabaseModel())
        }
    }

    suspend fun removeAllAsteroids() {
        withContext(Dispatchers.IO) {
            database.asteroidDataDao.clear()
        }
    }

}