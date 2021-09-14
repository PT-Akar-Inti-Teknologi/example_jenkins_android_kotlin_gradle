package com.mobile.android.handler.result

/**
 *  Wrapping result of every calculation
 *
 */
sealed class Result<out T> {
    data class Success<T>(val data: T): Result<T>()
    data class Failure<T>(val error: ErrorEntity): Result<T>()

    inline fun <X> fold(success: (T) -> X, failure: (ErrorEntity) -> X): X = when (this) {
        is Success -> success(this.data)
        is Failure -> failure(this.error)
    }
}