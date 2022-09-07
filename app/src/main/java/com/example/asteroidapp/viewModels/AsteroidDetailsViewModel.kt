package com.example.asteroidapp.viewModels

import android.app.Application
import androidx.lifecycle.*
import com.example.asteroidapp.dataBase.AsteroidData

class AsteroidDetailsViewModel(app: Application,asteroid: AsteroidData): AndroidViewModel(app) {
    private val _selectedAsteroid =MutableLiveData<AsteroidData>()
    val selectedAsteroid: LiveData<AsteroidData>
        get() = _selectedAsteroid

    init {
        _selectedAsteroid.value = asteroid
    }

    val displayAbsoluteMagnitude = Transformations.map(selectedAsteroid) {
        it.absoluteMagnitude.toString()+" au"
    }

    val displayDistanceFromEarth = Transformations.map(selectedAsteroid) {
        it.distanceFromEarth.toString()+" au"
    }

    val displayEstimatedDiameter = Transformations.map(selectedAsteroid) {
        it.estimatedDiameter.toString()+" km"
    }

    val displayRelativeVelocity = Transformations.map(selectedAsteroid) {
        it.relativeVelocity.toString()+" km/s"
    }

}