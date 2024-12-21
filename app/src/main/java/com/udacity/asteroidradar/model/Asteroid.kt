package com.udacity.asteroidradar.model

import android.os.Parcelable
import com.udacity.asteroidradar.database.AsteroidEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Asteroid
    (
        val id: Long,
        val codename: String,
        val closeApproachDate: String,
        val absoluteMagnitude: Double,
        val estimatedDiameter: Double,
        val relativeVelocity: Double,
        val distanceFromEarth: Double,
        val isPotentiallyHazardous: Boolean
    ) : Parcelable


enum class AsteroidFilter(val value: String) {
    SHOW_TODAY("today"),
    SHOW_WEEK("week"),
    SHOW_ALL("all")
}


fun Asteroid.asDatabaseModel(): AsteroidEntity {
    return AsteroidEntity(
        asteroidId = this.id,
        codename = this.codename,
        closeApproachDate = this.closeApproachDate,
        absoluteMagnitude = this.absoluteMagnitude,
        estimatedDiameter = this.estimatedDiameter,
        relativeVelocity = this.relativeVelocity,
        distanceFromEarth = this.distanceFromEarth,
        isPotentiallyHazardous = this.isPotentiallyHazardous)
}