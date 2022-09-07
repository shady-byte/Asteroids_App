package com.example.asteroidapp.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.asteroidapp.adapters.Constants
import com.example.asteroidapp.dataBase.AsteroidData
import com.example.asteroidapp.dataBase.AsteroidDatabaseDao
import com.example.asteroidapp.dataBase.NasaTodayImage
import com.example.asteroidapp.network.AsteroidApi
import com.example.asteroidapp.repositories.AsteroidRepository
import kotlinx.coroutines.launch

class AsteroidsListViewModel(app: Application,private val database: AsteroidDatabaseDao): AndroidViewModel(app) {
    private val repository = AsteroidRepository(database)

    private val _filterStatus = MutableLiveData<Boolean>(false)
    private val filterStatus: LiveData<Boolean>
        get() = _filterStatus

    val loading = MutableLiveData<Boolean>(false)

    private val _asteroidsList = MutableLiveData<List<AsteroidData>>()

    val asteroidsList : LiveData<List<AsteroidData>>
        get() = _asteroidsList

    private val _todayImage = MutableLiveData<NasaTodayImage>()
    val todayImage: LiveData<NasaTodayImage>
        get()= _todayImage

   init {
       updateFilter(filterStatus.value!!)
       fetchTodayImage()
       viewModelScope.launch {
           repository.refreshDatabase()
           loading.value = false
       }
   }

    fun fetchTodayImage() {
        viewModelScope.launch {
            try {
                val image = AsteroidApi.imageRetrofitService.getTodayImage(Constants.API_KEY)
                _todayImage.value = image
            } catch (ex: Exception) {
                //throw IllegalAccessException("something went wrong with downloading image ${ex.message}")
            }
        }
    }

    fun updateFilter(filter: Boolean) {
        _filterStatus.value = filter
        viewModelScope.launch{
            _asteroidsList.value = repository.getAsteroidsList(filter)
        }
    }

    fun clearDatabase() {
        viewModelScope.launch {
            database.clearDatabase()
        }
    }
}