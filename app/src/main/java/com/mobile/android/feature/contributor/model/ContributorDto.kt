package com.mobile.android.feature.contributor.model

data class ContributorDto(
    val gistsUrl: String,
    val reposUrl: String,
    val followingUrl: String,
    val starredUrl: String,
    val login: String,
    val followersUrl: String,
    val type: String,
    val url: String,
    val subscriptionsUrl: String,
    val receivedEventsUrl: String,
    val contributions: Int,
    val avatarUrl: String,
    val eventsUrl: String,
    val htmlUrl: String,
    val siteAdmin: Boolean,
    val id: Int,
    val gravatarId: String,
    val nodeId: String,
    val organizationsUrl: String
)