package com.mobile.android.dependencies.database

import androidx.room.TypeConverter

class ReposConverter {
    @TypeConverter
    fun stringToIntList(data: String?): List<Int>? {
        return data?.let {stringData->
            stringData.split(",").map {
                it.toIntOrNull()
            }
        }?.filterNotNull()
    }

    @TypeConverter
    fun intListToString(ints: List<Int>?): String? {
        return ints?.joinToString(",")
    }
}