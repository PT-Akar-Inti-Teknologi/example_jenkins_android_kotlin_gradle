package com.mobile.android.feature.searchrepository

import com.mobile.android.feature.searchrepository.model.RepoDto
import com.mobile.android.handler.Resource
import kotlinx.coroutines.flow.Flow

interface GetRepos {
    operator fun invoke(query: String): Flow<Resource<List<RepoDto>>>
}