package com.example.asteroidapp.workManagers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.asteroidapp.dataBase.AsteroidDatabase
import com.example.asteroidapp.repositories.AsteroidRepository

class AsteroidWorkManager(private val context: Context,params: WorkerParameters) : CoroutineWorker(context,params) {
    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val databaseDao = AsteroidDatabase.getInstance(context).databaseDao
        val repository = AsteroidRepository(databaseDao)
        return try {
            databaseDao.clearDatabase()
            repository.refreshDatabase()
            Result.success()
        } catch (ex: Exception) {
            Result.retry()
        }

    }
}