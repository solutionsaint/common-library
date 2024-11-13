package com.techlambda.common.networkUtils

import com.google.gson.JsonParser
import retrofit2.Response
import java.io.IOException
import java.net.UnknownHostException

suspend fun <T> makeApiCall(apiCall: suspend () -> Response<T>): ApiResponse<T> {
    return try {
        val response = apiCall()
        if (response.isSuccessful) {
            response.body()?.let {
                ApiResponse.Success(it)
            } ?: ApiResponse.Error("Empty response body")
        } else {
            val errorBody = response.errorBody()
            val error = if (errorBody != null) {
                val jsonString = errorBody.string()
                val jsonObject = JsonParser.parseString(jsonString).asJsonObject
                jsonObject["message"]?.asString ?: "Unknown error"
            } else {
                "Something went wrong. Please try again."
            }
            ApiResponse.Error("Error: $error")
        }
    } catch (e: UnknownHostException) {
        ApiResponse.Error("No Internet Connection")
    } catch (e: IOException) {
        ApiResponse.Error("Network error: ${e.message}")
    } catch (e: Exception) {
        ApiResponse.Error("Unexpected error: ${e.message}")
    }
}