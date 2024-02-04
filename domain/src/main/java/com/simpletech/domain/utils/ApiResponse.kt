package com.simpletech.domain.utils

sealed class ApiResponse<T> {
    data class Success<T>(val data: T) : ApiResponse<T>()
    data class Failure<T>(val error: Exception) : ApiResponse<T>()
}