package com.mobile.android.dependencies.http

import com.mobile.android.handler.result.Result

/**
 *  Wrap HTTP Request library for convenience to change
 *  the implementation in the future
 *
 *  Chosen because it is relatively easier than Retrofit (Sorry Jake)
 */

interface HttpClient {

    fun get(url : String): HttpClient

    fun log(tag : String): HttpClient

    fun header(header : String, value : Any): HttpClient

    fun body(payload : Any): HttpClient

    fun dispatch(): Result<String>
}