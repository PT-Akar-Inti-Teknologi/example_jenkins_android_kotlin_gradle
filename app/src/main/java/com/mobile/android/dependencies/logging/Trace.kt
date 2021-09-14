package com.mobile.android.dependencies.logging

import android.util.Log
import com.mobile.android.BuildConfig

/**
 *
 *  Wrap [android.util.Log] for more convenient way to disable terminal Log
 */
internal object Trace {

    private val isEnabled = BuildConfig.DEBUG
    fun d(tag: String, message: String){
        if(isEnabled) Log.d(tag, message)
    }

    fun e(tag: String, message: String){
        if(isEnabled) Log.e(tag, message)
    }

    fun exception(e: Throwable){
        if(isEnabled){
            e.printStackTrace()
        }
    }
}