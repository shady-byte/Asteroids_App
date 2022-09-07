package com.example.asteroidapp.adapters

import android.annotation.SuppressLint
import com.example.asteroidapp.BuildConfig
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object Constants {
    const val API_KEY = BuildConfig.API_KEY
    const val API_QUERY_DATE_FORMAT = "YYYY-MM-dd"
    private const val DEFAULT_END_DATE_DAYS = 7
    const val BASE_URL = "https://api.nasa.gov/"
    const val TODAY_IMAGE_URL = "https://api.nasa.gov/planetary/"


    @SuppressLint("WeekBasedYear")
    fun getNextSevenDaysFormattedDates(): ArrayList<String> {
        val formattedDateList = ArrayList<String>()

        val calendar = Calendar.getInstance()
        for (i in 0..DEFAULT_END_DATE_DAYS) {
            val currentTime = calendar.time
            val dateFormat = SimpleDateFormat(API_QUERY_DATE_FORMAT, Locale.getDefault())
            formattedDateList.add(dateFormat.format(currentTime))
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }
        return formattedDateList
    }
}