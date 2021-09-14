package com.mobile.android.dependencies.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mobile.android.feature.searchrepository.model.RepoSearchEntity
import com.mobile.android.feature.searchrepository.model.Repository
import kotlinx.coroutines.flow.Flow

@Dao
interface RepositoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepos(repositories: List<Repository>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSearchResult(result: RepoSearchEntity)

    @Query("SELECT * FROM search_result WHERE `query` = :query")
    fun loadSearchResult(query: String): RepoSearchEntity?

    @Query("SELECT * FROM repository WHERE id in (:repoIds)")
    fun loadById(repoIds: List<Int>): Flow<List<Repository>>
}