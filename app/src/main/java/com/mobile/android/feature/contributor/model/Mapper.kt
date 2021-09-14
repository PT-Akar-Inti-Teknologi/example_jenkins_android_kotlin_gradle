package com.mobile.android.feature.contributor.model

import com.mobile.android.handler.orFalse
import com.mobile.android.handler.orZero

fun ContributorResponse?.toEntity(): ContributorEntity{
    return ContributorEntity(
        gistsUrl = this?.gistsUrl,
        reposUrl = this?.reposUrl,
        followingUrl = this?.followingUrl,
        starredUrl = this?.starredUrl,
        login = this?.login,
        followersUrl = this?.followersUrl,
        type = this?.type,
        url = this?.url,
        subscriptionsUrl = this?.subscriptionsUrl,
        receivedEventsUrl = this?.receivedEventsUrl,
        contributions = this?.contributions,
        avatarUrl = this?.avatarUrl,
        eventsUrl = this?.eventsUrl,
        htmlUrl = this?.htmlUrl,
        siteAdmin = this?.siteAdmin,
        id = this?.id,
        gravatarId = this?.gravatarId,
        nodeId = this?.nodeId,
        organizationsUrl = this?.organizationsUrl
    )
}

fun List<ContributorEntity?>?.toDto(): List<ContributorDto> = this?.map { entity->
    ContributorDto(
        gistsUrl = entity?.gistsUrl.orEmpty(),
        reposUrl = entity?.reposUrl.orEmpty(),
        followingUrl = entity?.followingUrl.orEmpty(),
        starredUrl = entity?.starredUrl.orEmpty(),
        login = entity?.login.orEmpty(),
        followersUrl = entity?.followersUrl.orEmpty(),
        type = entity?.type.orEmpty(),
        url = entity?.url.orEmpty(),
        subscriptionsUrl = entity?.subscriptionsUrl.orEmpty(),
        receivedEventsUrl = entity?.receivedEventsUrl.orEmpty(),
        contributions = entity?.contributions.orZero(),
        avatarUrl = entity?.avatarUrl.orEmpty(),
        eventsUrl = entity?.eventsUrl.orEmpty(),
        htmlUrl = entity?.htmlUrl.orEmpty(),
        siteAdmin = entity?.siteAdmin.orFalse(),
        id = entity?.id.orZero(),
        gravatarId = entity?.gravatarId.orEmpty(),
        nodeId = entity?.nodeId.orEmpty(),
        organizationsUrl = entity?.organizationsUrl.orEmpty()
    )
} ?: emptyList()