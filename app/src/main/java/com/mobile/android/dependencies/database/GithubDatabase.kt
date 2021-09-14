package com.mobile.android.dependencies.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mobile.android.feature.contributor.model.ContributorEntity
import com.mobile.android.feature.searchrepository.model.RepoSearchEntity
import com.mobile.android.feature.searchrepository.model.Repository

@Database(
    entities = [ContributorEntity::class, RepoSearchEntity::class, Repository::class],
    version = 1,
    exportSchema = false)
@TypeConverters(ReposConverter::class)
abstract class GithubDatabase: RoomDatabase() {
    abstract fun contributorDao(): ContributorDao
    abstract fun repositoryDao(): RepositoryDao

    companion object{
        private const val NAME = "GithubDatabase"
        private var roomDatabase: GithubDatabase? = null
        fun getInstance(context: Context): GithubDatabase? {
            if (roomDatabase == null){
                roomDatabase = Room.databaseBuilder(context, GithubDatabase::class.java, NAME)
                    .setJournalMode(JournalMode.TRUNCATE)
                    .addMigrations()
                    .build()
            }
            return roomDatabase
        }
    }
}