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
        viewModelScope.launch {
            asteroidRepository.refreshAsteroids()
        }
    }

    fun onAsteroidClicked(asteroid: Asteroid) {
        _navigateToAsteroidDetails.value = asteroid
    }

    fun doneNavigating() {
        _navigateToAsteroidDetails.value = null
    }

}