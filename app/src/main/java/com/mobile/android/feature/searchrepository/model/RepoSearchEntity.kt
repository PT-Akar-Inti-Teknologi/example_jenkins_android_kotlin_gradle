package com.mobile.android.feature.searchrepository.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *  Used as model for database
 *
 */
@Entity(tableName = "search_result")
data class RepoSearchEntity(
    @PrimaryKey
    @ColumnInfo(name = "query")
    val query: String,

    @ColumnInfo(name = "repo_id")
    val repoIds: List<Int>,

    @ColumnInfo(name = "count")
    val totalCount: Int,
)