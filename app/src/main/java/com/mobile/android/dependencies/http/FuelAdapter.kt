package com.mobile.android.dependencies.http

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.gson.jsonBody
import com.mobile.android.dependencies.logging.Trace
import com.mobile.android.handler.result.*

internal class FuelAdapter: HttpClient {

    private lateinit var requestClient : Request
    override fun get(url: String): HttpClient = apply {
        requestClient = Fuel.get(url)
    }

    override fun log(tag: String): HttpClient = apply{
        requestClient.also {request->
            Trace.e(tag, "$request")
        }
    }

    override fun header(header: String, value: Any): HttpClient = apply {
        requestClient.header(header, value)
    }

    override fun body(payload: Any): HttpClient = apply {
        requestClient.jsonBody(payload)
    }

    override fun dispatch(): Result<String> {
        return requestClient.responseString().third.fold(
            success = {json->
                Result.Success(json)
            },
            failure = {error->
                Result.Failure(error.extractError())
            }
        )
    }
}

internal fun FuelError.extractError(): ErrorEntity {

    // If any
    Trace.exception(this.exception)
    val body = this.response.body().asString("application/json")
    Trace.d("FuelError", "body --- $body")
    if(body.contains("<html>", true)){
        return ServiceUnavailable("Service Unavailable at the moment")
    }

    return when{
        body.contains("400", true)-> {
            AccessDenied("Bad Request")
        }
        body.contains("404", true)->{
            AccessDenied("Not Found")
        }
        body.contains("500", true)->{
            ServiceUnavailable("Service you've requested is not found")
        }
        else-> {
            Unknown("Unknown Error Please Contact the Police")
        }
    }
}