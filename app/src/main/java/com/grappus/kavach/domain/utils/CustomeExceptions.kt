package com.grappus.kavach.domain.utils

class UnauthorizedException(message: String) : Exception(message)

// Custom exception for no internet connection
class NoInternetException(message: String) : Exception(message)

// Custom generic exception
class GenericException(message: String) : Exception(message)