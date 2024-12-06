package com.kocerlabs.weatherapp.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseRepository {

    suspend fun <T> safeApiCall(
        apicall: suspend () -> T
    ): T {
        return withContext(Dispatchers.IO) {
            var result: T? = null
            try {
                result = apicall.invoke()
                return@withContext result
            } catch (throwable: Exception) {
                throw throwable
            }
        }
    }

}