package com.simpletech.data.helpers


sealed class ApiException(override val message: String) : Exception(message) {
    data object EmptyBodyException : ApiException("No data found")
}