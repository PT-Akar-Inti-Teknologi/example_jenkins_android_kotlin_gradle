package com.mobile.android.handler.result

/**
 *  Wrapping Error
 *
 */
sealed class ErrorEntity(open val message: String)

data class NetworkError(override val message: String): ErrorEntity(message)

data class AccessDenied(override val message: String): ErrorEntity(message)

data class ServiceUnavailable(override val message: String): ErrorEntity(message)

data class Unknown(override val message: String): ErrorEntity(message)