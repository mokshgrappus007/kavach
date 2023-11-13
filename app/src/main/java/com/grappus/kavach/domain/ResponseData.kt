package com.grappus.kavach.domain

sealed class ResponseData<out T> {
    data class Success<out T>(val data: T) : ResponseData<T>()
    data class Error(val errorType: ErrorType) : ResponseData<Nothing>()
}

sealed class ErrorType {
    data class Unauthorized(val message: String) : ErrorType()
    data class NoInternetConnection(val message: String) : ErrorType()
    data class Generic(val message: String) : ErrorType()
}

