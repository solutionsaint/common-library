package com.techlambda.common.networkUtils

import android.util.Log
import kotlinx.coroutines.Dispatchers
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

// A function that performs a safe API call and handles exceptions
suspend fun <T> makeApiCall(apiCall: suspend () -> T): ApiResponse<T> {
    return withContext(Dispatchers.IO) {
        try {
            val data = apiCall()
            Log.d("API", "An success occurred: $data")
            ApiResponse.Success(data)
        } catch (e: ClientRequestException) {
            // This is where you handle 4xx errors like 401
            val errorResponse = extractErrorResponse(e.response)
            ApiResponse.Error("Error: ${errorResponse?.message}")
        } catch (e: RedirectResponseException) {
            // 3xx errors
            ApiResponse.Error("Redirect Error: ${e.response.status.description}")
        } catch (e: ServerResponseException) {
            // 5xx errors
            ApiResponse.Error("Server Error: ${e.response.status.description}")
        } catch (e: Exception) {
            Log.d("APIError", "An error occurred: ${e.message}")
            ApiResponse.Error(e.message ?: "An error occurred")
        }
    }
}

suspend fun extractErrorResponse(response: HttpResponse): ErrorResponse? {
    return try {
        val json = Json { ignoreUnknownKeys = true }
        val errorBody = response.bodyAsText()
        json.decodeFromString(ErrorResponse.serializer(), errorBody)
    } catch (e: Exception) {
        null
    }
}

@Serializable
data class ErrorResponse(val statusCode: String, val message: String)