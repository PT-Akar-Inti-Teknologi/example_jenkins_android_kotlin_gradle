package com.mobile.android.feature.searchrepository.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

data class RepoEntity(
    val totalCount: Int,

    val incompleteResults: Boolean,

    val items: List<Repository>
)

@Entity(tableName = "repository")
data class Repository(

    @ColumnInfo(name = "id")
    val id: Int? = null,

    @ColumnInfo(name = "star")
    val stargazersCount: Int? = null,

    @ColumnInfo(name = "full_name")
    val fullName: String? = null,

    @ColumnInfo(name = "html_url")
    val htmlUrl: String? = null,

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "name")
    val name: String = "",

    @ColumnInfo(name = "desc")
    val description: String? = null,

    @Embedded(prefix = "owner_")
    val owner: Owner? = null,
){
    data class Owner(

        @ColumnInfo(name = "login")
        val login: String? = null,

        @ColumnInfo(name = "url")
        val url: String? = null,
    )
}