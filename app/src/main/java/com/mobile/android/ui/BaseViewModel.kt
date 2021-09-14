package com.mobile.android.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

abstract class BaseViewModel: ViewModel() {
    fun <R> Flow<R>.asLiveDataViewModel(): LiveData<R>{
        return asLiveData(viewModelScope.coroutineContext+Dispatchers.IO)
    }
}