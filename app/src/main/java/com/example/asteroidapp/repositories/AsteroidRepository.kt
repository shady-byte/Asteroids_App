package com.example.asteroidapp.repositories

import android.util.Log
import com.example.asteroidapp.adapters.Constants
import com.example.asteroidapp.dataBase.AsteroidData
import com.example.asteroidapp.dataBase.AsteroidDatabaseDao
import com.example.asteroidapp.network.AsteroidApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class AsteroidRepository(private val database: AsteroidDatabaseDao) {
    //var asteroids: List<AsteroidData> = database.getAllAsteroids()

    suspend fun refreshDatabase() {
        withContext(Dispatchers.IO) {
            try {
                val dateList = Constants.getNextSevenDaysFormattedDates()
                val response = AsteroidApi.retrofitService.getAsteroidNearEarth(dateList.first(),
                    dateList.last(),Constants.API_KEY)
                val jsonObj = JSONObject(response)
                val asteroidList = convertJsonToAsteroids(jsonObj)
                database.insertAllAsteroids(*asteroidList.toTypedArray())
            } catch (ex: Exception) {
                //Log.d("MainTest","something went wrong with connection ${ex.message}")
            }
        }
    }

    suspend fun getAsteroidsList(filter: Boolean): MutableList<AsteroidData> {
        var asteroids= mutableListOf<AsteroidData>()
        withContext(Dispatchers.IO) {
            try {
                val todayString = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                asteroids = database.getAllAsteroids(todayString).toMutableList()
                if(filter) {
                    asteroids = database.getNewAsteroids(todayString).toMutableList()
                }
                return@withContext
            } catch (ex: Exception) {
                Log.d("MainTest","sorry Exception ${ex.message}")
            }
        }
        return asteroids
    }

    private fun convertJsonToAsteroids(obj: JSONObject) : List<AsteroidData> {
        val asteroidList = mutableListOf<AsteroidData>() //ArrayList<AsteroidData>()
        val nearEarthObjects = obj.getJSONObject("near_earth_objects")
        val datesList = Constants.getNextSevenDaysFormattedDates()
        for (date in datesList) {
            if(nearEarthObjects.has(date)) {
                val dateAsteroidArray = nearEarthObjects.getJSONArray(date)
                for(i in 0 until dateAsteroidArray.length()) {
                    val dateAsteroidJsonObject = dateAsteroidArray.getJSONObject(i)
                    val asteroidId = dateAsteroidJsonObject.getLong("id")
                    val asteroidName = dateAsteroidJsonObject.getString("name")
                    val asteroidMagnitude = dateAsteroidJsonObject.getDouble("absolute_magnitude_h")
                    val asteroidIsHazardous = dateAsteroidJsonObject.getBoolean("is_potentially_hazardous_asteroid")
                    val asteroidDiameter = dateAsteroidJsonObject.getJSONObject("estimated_diameter")
                        .getJSONObject("kilometers").getDouble("estimated_diameter_max")
                    val asteroidApproachData = dateAsteroidJsonObject.getJSONArray("close_approach_data")
                        .getJSONObject(0)
                    val asteroidVelocity = asteroidApproachData.getJSONObject("relative_velocity")
                        .getDouble("kilometers_per_second")
                    val asteroidDistanceFromEarth = asteroidApproachData.getJSONObject("miss_distance")
                        .getDouble("astronomical")
                    val asteroidApproachDate =asteroidApproachData.getString("close_approach_date")


                    val asteroid = AsteroidData(asteroidId,asteroidName,asteroidMagnitude,asteroidIsHazardous,asteroidDiameter
                        ,asteroidVelocity,asteroidDistanceFromEarth,asteroidApproachDate)
                    asteroidList.add(asteroid)
                }
            }
        }
        return  asteroidList
    }

    /*
    val todayString = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    val todayDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        .parse(todayString)

    asteroids.removeAll { data ->
        val firstDate= SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            .parse(data.closeApproachDate)
        //remove all data of dates less than today
        firstDate?.compareTo(todayDate) == -1
    }
     */
}