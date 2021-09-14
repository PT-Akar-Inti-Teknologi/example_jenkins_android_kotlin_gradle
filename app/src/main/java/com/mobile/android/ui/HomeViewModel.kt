package com.mobile.android.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.mobile.android.feature.contributor.GetContributor
import com.mobile.android.feature.contributor.model.ContributorDto
import com.mobile.android.feature.searchrepository.GetRepos
import com.mobile.android.feature.searchrepository.model.RepoDto
import com.mobile.android.handler.Resource
import kotlinx.coroutines.flow.MutableStateFlow

class HomeViewModel(
    private val getContributor: GetContributor,
    private val getRepos: GetRepos
): BaseViewModel() {

    private val _keywords: MutableStateFlow<String> = MutableStateFlow("")
    val contributor: LiveData<Resource<List<ContributorDto>>> = getContributor().asLiveDataViewModel()
    val repos: LiveData<Resource<List<RepoDto>>> = _keywords.asLiveData().switchMap {keywords->
        getRepos(query = keywords).asLiveDataViewModel()
    }

    fun setKeywords(keywords: String){
        _keywords.value = keywords
    }
}