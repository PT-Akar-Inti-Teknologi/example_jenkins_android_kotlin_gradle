package com.mobile.android.feature.searchrepository.model

/**
 *  This one intended to be passed around and for UI
 *
 */
data class RepoDto(
    val id: Int,

    val stargazersCount: Int,

    val fullName: String,

    val name: String,

    val description: String,

    val htmlUrl: String,

    val owner: Owner,
) {
    data class Owner(

        val login: String,

        val url: String,
    )
}