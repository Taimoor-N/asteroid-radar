package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.asDatabaseModel
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.network.AsteroidApi
import com.udacity.asteroidradar.network.parseAsteroidsJsonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class AsteroidRepository(private val database: AsteroidDatabase) {

    private val sdf = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())

    val asteroids: LiveData<List<Asteroid>> = database.asteroidDataDao.getAllAsteroids()
        .map { it.asDomainModel() }

    suspend fun addAsteroid(asteroid: Asteroid) {
        withContext(Dispatchers.IO) {
            database.asteroidDataDao.insert(asteroid.asDatabaseModel())
        }
    }

    suspend fun removePastAsteroids() {
        withContext(Dispatchers.IO) {
            database.asteroidDataDao.clearAsteroidsBeforeDate(sdf.format(Date()))
        }
    }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            try {
                val asteroidJsonResponse = AsteroidApi.retrofitService.getAsteroids(startDate = sdf.format(Date()))
                val parsedAsteroids = parseAsteroidsJsonResult(JSONObject(asteroidJsonResponse))
                for (asteroid in parsedAsteroids) {
                    addAsteroid(asteroid)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
        }
    }

}