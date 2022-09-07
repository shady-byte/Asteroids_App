package com.example.asteroidapp.viewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.asteroidapp.dataBase.AsteroidData
import com.example.asteroidapp.dataBase.AsteroidDatabaseDao

class ViewModelListFactory(private val database: AsteroidDatabaseDao, private val app:Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AsteroidsListViewModel::class.java)) {
            return AsteroidsListViewModel(app,database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class ViewModelDetailsFactory(private val app: Application,private val asteroid: AsteroidData): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AsteroidDetailsViewModel::class.java)) {
            return AsteroidDetailsViewModel(app,asteroid) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}