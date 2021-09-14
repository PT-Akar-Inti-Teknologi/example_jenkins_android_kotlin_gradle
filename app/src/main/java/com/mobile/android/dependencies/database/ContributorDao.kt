package com.mobile.android.dependencies.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mobile.android.feature.contributor.model.ContributorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ContributorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContributors(list: List<ContributorEntity>)

    @Query("SELECT * FROM contributor ORDER BY contributions DESC")
    fun loadContributors(): Flow<List<ContributorEntity?>?>
}