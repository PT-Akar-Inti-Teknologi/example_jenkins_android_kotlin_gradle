package com.mobile.android.feature.searchrepository.model

import com.mobile.android.handler.orFalse
import com.mobile.android.handler.orZero

fun RepoResponse.toEntity() = RepoEntity(
    totalCount = totalCount.orZero(),
    incompleteResults = incompleteResults.orFalse(),
    items = items?.map { item->
        Repository(
            id = item?.id,
            stargazersCount = item?.stargazersCount,
            fullName = item?.fullName,
            name = item?.fullName.orEmpty(),
            description = item?.description,
            owner = Repository.Owner(
                login = item?.owner?.login,
                url = item?.owner?.url
            ),
            htmlUrl = item?.htmlUrl
        )
    }?: emptyList()
)

fun List<Repository>.toDtoList(): List<RepoDto>{
    return this.map { repository ->
        RepoDto(
            id = repository.id.orZero(),
            stargazersCount = repository.stargazersCount.orZero(),
            fullName = repository.fullName.orEmpty(),
            name = repository.fullName.orEmpty(),
            description = repository.description.orEmpty(),
            htmlUrl = repository.htmlUrl.orEmpty(),
            owner = RepoDto.Owner(
                login = repository.owner?.login.orEmpty(),
                url = repository.owner?.url.orEmpty()
            )
        )
    }
}