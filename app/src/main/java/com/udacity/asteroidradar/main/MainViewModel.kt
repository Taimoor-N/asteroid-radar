package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AsteroidDatabase.getInstance(application)
    private val asteroidRepository = AsteroidRepository(database)

    val asteroids: LiveData<List<Asteroid>> = asteroidRepository.asteroids

    private val _navigateToAsteroidDetails = MutableLiveData<Asteroid?>()

    val navigateToAsteroidDetails: LiveData<Asteroid?>
        get() = _navigateToAsteroidDetails

    init {
        createTestAsteroids()
    }

    fun onAsteroidClicked(asteroid: Asteroid) {
        _navigateToAsteroidDetails.value = asteroid
    }

    fun doneNavigating() {
        _navigateToAsteroidDetails.value = null
    }

    private fun createTestAsteroids() {
        viewModelScope.launch {
            asteroidRepository.removeAllAsteroids()
            asteroidRepository.addAsteroid(Asteroid(1000001, "111111 (2009 AR5)", "2015-09-08", 20.44, 0.4853331752, 18.1279360862, 0.3027469457, true))
            asteroidRepository.addAsteroid(Asteroid(1000002, "222222 (2012 BR5)", "2015-09-08", 11.53, 0.1853331234, 28.1443360862, 0.6027465324, false))
            asteroidRepository.addAsteroid(Asteroid(1000003, "333333 (2014 CR5)", "2015-09-08", 23.34, 0.5553331153, 19.2342360862, 0.2032325422, true))
            asteroidRepository.addAsteroid(Asteroid(1000004, "444444 (2017 DR5)", "2015-09-08", 12.22, 0.0185331723, 11.4452342432, 0.8142132133, false))
            asteroidRepository.addAsteroid(Asteroid(1000005, "555555 (2021 ER5)", "2015-09-08", 11.97, 0.2053331533, 33.1231233231, 0.9515734574, false))
        }
    }

}

// Update LiveData with its own value to notify observers when it's value changes
fun <T> MutableLiveData<T>.notifyObserver() {
    this.value = this.value
}