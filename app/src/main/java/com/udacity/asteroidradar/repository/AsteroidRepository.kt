package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.model.AsteroidFilter
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.model.asDatabaseModel
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.model.PictureOfDay
import com.udacity.asteroidradar.network.AsteroidApi
import com.udacity.asteroidradar.network.PictureOfDayApi
import com.udacity.asteroidradar.network.parseAsteroidsJsonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class AsteroidRepository(private val database: AsteroidDatabase) {

    private val sdf = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())

    fun getAsteroids(filter: AsteroidFilter) : LiveData<List<Asteroid>> {
        val calendar = Calendar.getInstance()
        val t1 = calendar.time
        when(filter) {
            AsteroidFilter.SHOW_ALL -> {
                return database.asteroidDataDao.getAllAsteroids().map { it.asDomainModel() }
            }
            AsteroidFilter.SHOW_TODAY -> {
                calendar.add(Calendar.DATE, 1)
                val t2 = calendar.time
                return database.asteroidDataDao.getAsteroidsForDatesBetween(sdf.format(t1), sdf.format(t2))
                    .map { it.asDomainModel() }
            }
            AsteroidFilter.SHOW_WEEK -> {
                calendar.add(Calendar.DATE, 7)
                val t2 = calendar.time
                return database.asteroidDataDao.getAsteroidsForDatesBetween(sdf.format(t1), sdf.format(t2))
                    .map { it.asDomainModel() }
            }
        }
    }

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
            }
        }
    }

    suspend fun getPictureOfDay() : PictureOfDay? {
        var pictureOfDay : PictureOfDay? = null
        withContext(Dispatchers.IO) {
            try {
                pictureOfDay = PictureOfDayApi.retrofitService.getPictureOfDay()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return pictureOfDay
    }

}