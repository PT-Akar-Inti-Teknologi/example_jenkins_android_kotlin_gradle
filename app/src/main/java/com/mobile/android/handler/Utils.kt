package com.mobile.android.handler

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

inline fun <reified T> genericType(): Type = object: TypeToken<T>() {}.type
fun Int?.orZero() = this ?: 0
fun Boolean?.orFalse() = this ?: false

class Deserializer<T: Any> (private val javaClassName: Class<T>) {
    fun deserialize(content: String): T = Gson().fromJson(content, javaClassName)
}