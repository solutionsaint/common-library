package com.techlambda.common.networkUtils

// Define a sealed class to represent the result of the API call
sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val exception: String) : ApiResponse<Nothing>()
}

// Extension function for ApiResponse to handle success cases
inline fun <T> ApiResponse<T>.onSuccess(action: (T) -> Unit): ApiResponse<T> {
    if (this is ApiResponse.Success) {
        action(data)
    }
    return this
}

// Extension function for ApiResponse to handle error cases
inline fun <T> ApiResponse<T>.onError(action: (String) -> Unit): ApiResponse<T> {
    if (this is ApiResponse.Error) {
        action(exception)
    }
    return this
}
