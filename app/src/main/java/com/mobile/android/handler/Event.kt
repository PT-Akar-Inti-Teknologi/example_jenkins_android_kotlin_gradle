package com.mobile.android.handler

/**
 *  To emulate single life event, but unfortunately unused
 *
 */
class Event<out T>(private val data: T) {

    private var hasBeenHandled: Boolean = false
    fun getData(): T? {
        return if(hasBeenHandled){
            null
        } else {
            hasBeenHandled = true
            data
        }
    }

    fun peekData(): T {
        return data
    }
}