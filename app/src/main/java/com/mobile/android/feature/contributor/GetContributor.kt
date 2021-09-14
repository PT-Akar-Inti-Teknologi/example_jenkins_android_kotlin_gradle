package com.mobile.android.feature.contributor

import com.mobile.android.feature.contributor.model.ContributorDto
import com.mobile.android.handler.Resource
import kotlinx.coroutines.flow.Flow

interface GetContributor {
    operator fun invoke(): Flow<Resource<List<ContributorDto>>>
}